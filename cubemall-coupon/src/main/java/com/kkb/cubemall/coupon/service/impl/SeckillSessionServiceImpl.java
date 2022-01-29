package com.kkb.cubemall.coupon.service.impl;



import com.kkb.cubemall.common.utils.DateTimeUtils;
import com.kkb.cubemall.coupon.entity.SeckillSkuRelationEntity;
import com.kkb.cubemall.coupon.service.SeckillSkuRelationService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.coupon.dao.SeckillSessionDao;
import com.kkb.cubemall.coupon.entity.SeckillSessionEntity;
import com.kkb.cubemall.coupon.service.SeckillSessionService;


@Service("seckillSessionService")
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionDao, SeckillSessionEntity> implements SeckillSessionService {

    @Autowired
    private SeckillSkuRelationService seckillSkuRelationService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SeckillSessionEntity> page = this.page(
                new Query<SeckillSessionEntity>().getPage(params),
                new QueryWrapper<SeckillSessionEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 上架最近3天的秒杀商品
     * 当天 00:00:00 - 第2天 00:00:00
     * 第2天 00:00:00 - 第3天 00:00:00
     * 第3天 00:00:00 - 第4天 00:00:00
     * @return
     */
    @Override
    public List<SeckillSessionEntity> getLates3DaySession() {

        String start = DateTimeUtils.getDateTime(0);
        String end = DateTimeUtils.getDateTime(3);

        System.out.println("s:"+start+",e:"+end);
        //查询最近3天的活动信息
        List<SeckillSessionEntity> list =
                this.baseMapper.selectList(new QueryWrapper<SeckillSessionEntity>().between("start_time",start,end));

        System.out.println("listsize:"+(list==null?0:list.size()));

        if(list==null||list.size()<=0){
            return null;
        }

        //查询活动信息关联的商品

        List<SeckillSessionEntity> collect = list.stream().map(session->{
          Long id = session.getId();
          //查询sms_seckill_sku_relation表中关联skuId
            List<SeckillSkuRelationEntity> relationEntities =
                    seckillSkuRelationService.list(new QueryWrapper<SeckillSkuRelationEntity>().eq("promotion_session_id",id));
            session.setRelationEntities(relationEntities);
            return session;
        }).collect(Collectors.toList());

        return collect;
    }

}
