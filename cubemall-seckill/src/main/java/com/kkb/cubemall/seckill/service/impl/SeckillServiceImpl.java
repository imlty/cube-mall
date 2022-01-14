package com.kkb.cubemall.seckill.service.impl;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.kkb.cubemall.common.to.SeckillOrderTo;
import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.common.vo.MemberResponseVo;
import com.kkb.cubemall.seckill.feign.CouponFeignService;
import com.kkb.cubemall.seckill.feign.ProductFeignService;
import com.kkb.cubemall.seckill.interceptor.LoginUserInterceptor;
import com.kkb.cubemall.seckill.service.SeckillService;
import com.kkb.cubemall.seckill.to.SeckillSkuRedisTo;
import com.kkb.cubemall.seckill.vo.SeckillSessionWithSkusVo;
import com.kkb.cubemall.seckill.vo.SkuInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //存储到redis，秒杀活动信息key前缀
    private final String SESSION_CACHE_PREFIX = "seckill:sessions:";

    //商品详细信息的前缀
    private final String SECKILL_CHARE_PREFIX = "seckill:skus";

    //库存的前缀
    private final String SKU_STOCK_SEMAPHORE = "seckill:stock:";


    public List<SeckillSkuRedisTo> getCurrentSeckillSuksBlockHandler(BlockException ex){
            log.error("getCurrentSeckillSuksBlockHandler被限流");
            return null;
    }

    //查询当前的秒杀商品列表
    @SentinelResource(value = "seckillSuksResource",blockHandler = "getCurrentSeckillSuksBlockHandler")
    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSuks() {

        //查询当前时间
        long currentTime = System.currentTimeMillis();

        List<SeckillSkuRedisTo> result = new ArrayList<>();

        //查询所有的活动场次
        Set<String> keys = redisTemplate.keys(SESSION_CACHE_PREFIX+"*");

        if(keys==null||keys.isEmpty()){
            return null;
        }

        try(Entry entry = SphU.entry("seckillSuks")){
            for(String key:keys){
                //seckill:sessions:1622649600000_1624982400000
                String replace = key.replace(SESSION_CACHE_PREFIX,"");//1622649600000_1624982400000
                String[] array = replace.split("_");
                long startTime = Long.parseLong(array[0]);//开始时间
                long endTime =Long.parseLong(array[1]);//结束时间

//            log.info("startTime:"+startTime+",endTime:"+endTime+",currentTime:"+currentTime);
                //判断当前场次是否进行秒杀
                if(currentTime>=startTime&&currentTime<=endTime){
                    //获取这个场次所有的商品信息
                    List<String> range =  redisTemplate.opsForList().range(key,-100,100); //4-18

                    BoundHashOperations<String,String,String> hasOps
                            = redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);

                    List<String> listValue = hasOps.multiGet(range);

                    if(listValue!=null&&!listValue.isEmpty()){
                        List<SeckillSkuRedisTo> collect = listValue.stream().map(item->{
                            String items = (String)item;
                            SeckillSkuRedisTo redisTo = JSON.parseObject(items,SeckillSkuRedisTo.class);
                            return redisTo;
                        }).collect(Collectors.toList());
                        result.addAll(collect);
                    }

                }


            }
        }catch (BlockException e){
            log.error("seckillSuks资源被限制",e);
        }



        return result;
    }


    /**
     * 根据skuid查询商品秒杀详情
     * @param skuId
     * @return
     */

    @Override
    public SeckillSkuRedisTo getSeckillSkuRedisTo(Long skuId) {

        BoundHashOperations<String,String,String> hashOps = redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
        //查询seckill:skus下所有的key值
        Set<String> keys = hashOps.keys();
        if(keys==null||keys.isEmpty()){
            return null;
        }
        //4-18 活动id-skuId
        String reg = "\\d-"+skuId;
        for (String key:keys){
            //开始遍历
            if(Pattern.matches(reg,key)){
                //如果匹配成功，说明找到当前的skuid
                String redisvalue=  hashOps.get(key);
                //进行反序列化
                SeckillSkuRedisTo redisTo = JSON.parseObject(redisvalue,SeckillSkuRedisTo.class);

                Long currentTime = System.currentTimeMillis();
                Long startTime = redisTo.getStartTime();
                Long endTime = redisTo.getEndTime();
                if(currentTime>=startTime&&currentTime<=endTime){
                    return redisTo;
                }
                redisTo.setRandomCode(null);
                return redisTo;
            }
        }

        return null;
    }

    /**
     * 当前商品进行秒杀
     * @param killId
     * @param key
     * @param num
     * @return
     */
    @Override
    public String kill(String killId, String key, Integer num) {
        long s1 = System.currentTimeMillis();
        //获取当前登录用户
        MemberResponseVo user = LoginUserInterceptor.loginUser.get();
        BoundHashOperations<String,String,String> hashOps =
                redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
        String skuInfoValue = hashOps.get(killId);
        if(StringUtils.isEmpty(skuInfoValue)){
            return null;
        }

        SeckillSkuRedisTo redisTo = JSON.parseObject(skuInfoValue,SeckillSkuRedisTo.class);

        Long startTime = redisTo.getStartTime();
        Long endTime = redisTo.getEndTime();
        long currentTime = System.currentTimeMillis();
        //判断当前这个秒杀是否在活动时间区间内(校验时间的合法性)
        if(currentTime>=startTime&&currentTime<=endTime){
            String randomCode = redisTo.getRandomCode();
            String skuId = redisTo.getPromotionSessionId() + "-"+redisTo.getSkuId();
            //校验随机码和商品ID
            if(randomCode.equals(key) && killId.equals(skuId)){
                Integer seckillLimit = redisTo.getSeckillLimit();
                String seckillCount = redisTemplate.opsForValue().get(SKU_STOCK_SEMAPHORE+randomCode);
                Integer count = Integer.parseInt(seckillCount);
                //判断信号量是否大于0，并且购买的数量不能超过库存
                if(count>0&& num <= seckillLimit && count > num){
                    String redisKeys = user.getId() + "-" +skuId;
                    Long ttl = endTime - currentTime;
                    Boolean aboolean = redisTemplate.opsForValue().setIfAbsent(redisKeys,num.toString(),ttl, TimeUnit.MILLISECONDS);
                    //占位成功说明从来没有购买过，
                    if(aboolean){
                        RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE+randomCode);
                        try {
                           boolean  semaphoreCount = semaphore.tryAcquire(num,100,TimeUnit.MILLISECONDS);
                            //说明还有库存
                            if(semaphoreCount){
                                String timeid = IdWorker.getTimeId();
                                //TODO 发送消息到MQ
                                SeckillOrderTo orderTo = new SeckillOrderTo();
                                orderTo.setOrderSn(timeid);
                                orderTo.setMemberId(user.getId());
                                orderTo.setNum(num);
                                orderTo.setPromotionSessionId(redisTo.getPromotionSessionId());
                                orderTo.setSkuId(redisTo.getSkuId());
                                orderTo.setSeckillPrice(redisTo.getSeckillPrice());
                                rabbitTemplate.
                                        convertAndSend("order-event-exchange","order.seckill.order",orderTo);


                                long s2 = System.currentTimeMillis();
                                log.info("耗时:"+(s2-s1));
                                return timeid;
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                }
            }

        }



        return null;
    }

    /**
     * 上架最近3天的需要秒杀的商品
     */
    @Override
    public void uploadSeckillSkuLateset3Days() {
        //1、获取最近3天的需要秒杀的活动
        R lastes3DaysSession = couponFeignService.getLates3DaySession();
        if(lastes3DaysSession.getCode() == 0){
            List<SeckillSessionWithSkusVo> sessionData =
                    lastes3DaysSession.getData("data",new TypeReference<List<SeckillSessionWithSkusVo>>(){});
            if(sessionData==null||sessionData.size()<=0){
                return;
            }
            //2、缓存活动信息
            saveSessionInfos(sessionData);

            //3、缓存活动关联的商品信息
            saveSessionSkuInfos(sessionData);
        }
    }


    //缓存秒杀活动信息到Redis
    private void saveSessionInfos(List<SeckillSessionWithSkusVo> sessionData) {
        sessionData.stream().forEach(session->{

            //获取当前活动的开始时间和结束的时间戳
            long startTime = session.getStartTime().getTime();
            long endTime = session.getEndTime().getTime();

            //生成Redis的key值
            String key = SESSION_CACHE_PREFIX + startTime +"_"+ endTime;
            log.info("saveSessionInfos-key="+key);


            Boolean hashkey =redisTemplate.hasKey(key);
            //判断Redis是否已有该活动信息，如果没有才进行添加
            if(!hashkey){
                //缓存活动信息
                //获取缓存value,活动ID+skuID
                List<String> sessionSkuIds = session.getRelationEntities().stream()
                        .map(item->item.getPromotionSessionId()+"-"+item.getSkuId().toString()
                            ).collect(Collectors.toList());
                redisTemplate.opsForList().leftPushAll(key,sessionSkuIds);
            }
        });

    }

    //缓存秒杀活动关联商品信息
    private void saveSessionSkuInfos(List<SeckillSessionWithSkusVo> sessionData) {

        sessionData.stream().forEach(session->{

            //准备hash操作，绑定hash值
            BoundHashOperations<String,Object,Object> operations =
                    redisTemplate.boundHashOps(SECKILL_CHARE_PREFIX);
            session.getRelationEntities().stream().forEach(seckillSkuVo -> {
                //构建Rediskey
                String redisKey = seckillSkuVo.getPromotionSessionId().toString()+"-"+
                        seckillSkuVo.getSkuId().toString();
                log.info("rediskey:"+redisKey);
                //判断当前的key是否存在进行,如果不存在则进行缓存
                if(!operations.hasKey(redisKey)){
                    //缓存sku商品信息
                    SeckillSkuRedisTo seckillSkuRedisTo = new SeckillSkuRedisTo();
                    //获取到SKUID
                    Long skuId = seckillSkuVo.getSkuId();
                    log.info("skudId:"+skuId);
                    //1) 先查询sku的基本信息，通过feign远程调用
                    R info = productFeignService.getSkuInfo(skuId);

                    if(info.getCode()==0){
                        SkuInfoVo skuInfoVo = info.getData("skuInfo",new TypeReference<SkuInfoVo>(){});
                        seckillSkuRedisTo.setSkuInfo(skuInfoVo);
                    }

                    //2) 设置sku的秒杀信息
                    BeanUtils.copyProperties(seckillSkuVo,seckillSkuRedisTo);

                    //3) 设置当前商品的秒杀时间
                    seckillSkuRedisTo.setStartTime(session.getStartTime().getTime());
                    seckillSkuRedisTo.setEndTime(session.getEndTime().getTime());

                    //4) 设置商品的随机码(防止恶意攻击)
                    String randcode = UUID.randomUUID().toString().replace("-","");
                    seckillSkuRedisTo.setRandomCode(randcode);

                    String seckillValue = JSON.toJSONString(seckillSkuRedisTo);
                    //缓存秒杀商品的详细信息
                    operations.put(redisKey,seckillValue);

                    //5) 使用库存作为分布式Redisson信号量(限流)
                    RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE+randcode);
                    //商品秒杀数量数量作为信息量
                    semaphore.trySetPermits(seckillSkuVo.getSeckillCount());
                }
            });
        });
    }




}
