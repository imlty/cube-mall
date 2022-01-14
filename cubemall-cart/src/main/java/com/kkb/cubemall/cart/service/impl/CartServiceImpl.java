package com.kkb.cubemall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.kkb.cubemall.cart.dto.UserInfoDTO;
import com.kkb.cubemall.cart.interceptor.CartInterceptor;
import com.kkb.cubemall.cart.rpc.ProductRemoteClient;
import com.kkb.cubemall.cart.service.CartService;
import com.kkb.cubemall.cart.utils.Constants;
import com.kkb.cubemall.cart.vo.CartItemVo;
import com.kkb.cubemall.cart.vo.CartVo;
import com.kkb.cubemall.cart.vo.SkuInfoVo;
import com.kkb.cubemall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @ClassName CartServiceImpl
 * @Description
 * @Author hubin
 * @Date 2021/5/26 16:57
 * @Version V1.0
 **/
@Slf4j
@Service
public class CartServiceImpl implements CartService {

    // 注入redistemplate
    @Autowired
    private RedisTemplate redisTemplate;

    // 注入线程池对象
    @Autowired
    private ThreadPoolExecutor executor;

    // 注入商品远程feign调用接口
    @Autowired
    private ProductRemoteClient productRemoteClient;


    /**
     * @Description: 购物车添加的接口
     * @Author: hubin
     * @CreateDate: 2021/5/26 16:56
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/26 16:56
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     * 业务步骤：
     * 1、获取购物车中已经存在的数据
     * 2、判断当前添加的商品是否在购物车中已经存在，如果已经存在这个商品，此时添加商品的数量相加即可
     * 3、如果此时购物车不存在这个商品，需要新添加商品进入购物车
     */
    @Override
    public CartItemVo addToCart(Long skuId, Integer num) {

        // 1、获取购物车数据，不论用户是否登录
        BoundHashOperations<String,Long,Object> cartOps = this.getCartFromRedis();

        // 2、判断当前添加的商品是否在购物车中已经存在
        String str = (String) cartOps.get(skuId);
        if(StringUtils.isBlank(str)){
            // 此时添加的商品在购物车中并不存在
            // 新创建购物车商品对象，组装购物车数据
            CartItemVo itemVo = new CartItemVo();
            // 异步编排
            CompletableFuture<Void> skuInfoFuture = CompletableFuture.runAsync(() -> {
                // 远程调用商品服务，获取商品信息
                R r = productRemoteClient.getSkuInfo(skuId);
                // 获取购物车数据
                SkuInfoVo skuInfoVo = r.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                });
                // 给购物车数据赋值
                itemVo.setTitle(skuInfoVo.getSkuTitle());
                itemVo.setSkuId(skuInfoVo.getId());
                itemVo.setImage(skuInfoVo.getSkuDefaultImg());
                itemVo.setPrice(skuInfoVo.getPrice());
                itemVo.setCount(num);
            }, executor);

            // 查询商品规格属性值
            CompletableFuture<Void> attrFuture = CompletableFuture.runAsync(() -> {
                // 远程调用商品服务接口，获取属性组合
                List<String> skuSaleAttrs = productRemoteClient.getSkuSaleAttrs(skuId);
                itemVo.setSkuAttr(skuSaleAttrs);

            }, executor);

            // 等到所有的异步任务全部完成
            try {
                CompletableFuture.allOf(skuInfoFuture,attrFuture).get();

                // 把组装的数据添加到购物车：redis
                String cartJson = JSON.toJSONString(itemVo);
                cartOps.put(skuId,cartJson);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            return itemVo;
        }else{
            // 此时添加的商品在购物车中存在
            CartItemVo itemVo = JSON.parseObject(str, CartItemVo.class);
            // 商品数量相加即可
            itemVo.setCount(itemVo.getCount() + num);

            // 更新购物车中数据即可： redis
            String cartJsonStr = JSON.toJSONString(itemVo);
            // 放入购物车即可
            cartOps.put(skuId,cartJsonStr);

            return itemVo;
        }


    }

    // 查询此次添加的购物车数据
    @Override
    public CartItemVo getCartItemData(Long skuId) {

        BoundHashOperations<String, Long, Object> cartFromRedis = this.getCartFromRedis();
        // 根据skuId获取用户数据
        String itemJson = (String) cartFromRedis.get(skuId);

        // 把json字符串变成一个对象
        CartItemVo cartItemVo = JSON.parseObject(itemJson, CartItemVo.class);

        return cartItemVo;
    }

    /**
     * @Description: 购物车结算页面： 购物车列表页面
     * @Author: hubin
     * @CreateDate: 2021/5/27 14:04
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/27 14:04
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @Override
    public CartVo getCartList() {

        // 创建购物车数据对象
        CartVo cartVo = new CartVo();
        // 获取用户信息
        UserInfoDTO userInfoDTO = CartInterceptor.dtoThreadLocal.get();
        // 判断用户是登录还是未登录
        if(userInfoDTO.getUserId() != null){
            // 用户登录状态
            String loginCartKey = Constants.CART_PREFIX + userInfoDTO.getUserId();
            // 临时用户key,在用户登录状态，合并购物车数据
            String tempCartKey = Constants.CART_PREFIX + userInfoDTO.getUserKey();

            // 获取临时用户购物车数据
            List<CartItemVo> tempCartList = this.getCartListFromRedis(tempCartKey);
            // 判断临时购物车数据是否存在，如果存在，合并购物车
            if(tempCartList != null){
                for (CartItemVo cartItemVo : tempCartList) {
                    // 添加购物车方法，直接合并即可
                    addToCart(cartItemVo.getSkuId(),cartItemVo.getCount());
                }

                // 清除掉临时购物车
                redisTemplate.delete(tempCartKey);

            }

            // 用户处于登录状态，获取合并后购物车的所有的数据
            List<CartItemVo> cartList = getCartListFromRedis(loginCartKey);
            cartVo.setItems(cartList);

        }else{
            // 用户未登录
            String tempCartKey = Constants.CART_PREFIX + userInfoDTO.getUserKey();
            // 获取临时购物车数据
            List<CartItemVo> cartList = this.getCartListFromRedis(tempCartKey);
            cartVo.setItems(cartList);
        }


        return cartVo;
    }

    /**
     * @Description: 购物车数量的改变
     * @Author: hubin
     * @CreateDate: 2021/5/27 17:40
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/27 17:40
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @Override
    public void changeItemNum(Long skuId, Integer num) {
        CartItemVo cartItemData = this.getCartItemData(skuId);
        // 添加商品数量
        cartItemData.setCount(num);
        // 获取购物车数据
        BoundHashOperations<String, Long, Object> cartFromRedis = this.getCartFromRedis();
        // 把购物车数据转换JOSN字符串
        String cartJSON = JSON.toJSONString(cartItemData);
        // 放回购物车数据
        cartFromRedis.put(skuId,cartJSON);
    }

    /**
     * @Description: 购物车商品删除实现
     * @Author: hubin
     * @CreateDate: 2021/5/27 18:06
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/27 18:06
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @Override
    public void deleteCart(Long skuId) {
        //  获取购物车数据对象
        BoundHashOperations<String, Long, Object> cartFromRedis = this.getCartFromRedis();
        // 清楚购物车中数据
        cartFromRedis.delete(skuId);
    }

    /**
     * @Description: 订单结算获取购物清单
     * @Author: hubin
     * @CreateDate: 2021/5/31 15:45
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/31 15:45
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @Override
    public List<CartItemVo> getCartitemList() {

        // 获取用户当前登录信息
        UserInfoDTO userInfoDTO = CartInterceptor.dtoThreadLocal.get();
        // 判断用户是否登录
        if(userInfoDTO.getUserId() != null){
            String cartKey = Constants.CART_PREFIX + userInfoDTO.getUserId();
            // 获取购物车数据项
            List<CartItemVo> cartListFromRedis = this.getCartListFromRedis(cartKey);

            if(cartListFromRedis!=null && cartListFromRedis.size()>0){
                List<CartItemVo> cartItemVoList = cartListFromRedis.stream()
                        .filter(items -> items.isCheck())
                        .map(item -> {
                            // 更新订单商品的最新的架构
                            BigDecimal price = productRemoteClient.getPrice(item.getSkuId());
                            item.setPrice(price);
                            return item;
                        })
                        .collect(Collectors.toList());

                // 登录状态获取的购物车清单
                return cartItemVoList;
            }

        }
        return null;
    }


    /**
     * @Description: 根据key获取购物车数据
     * @Author: hubin
     * @CreateDate: 2021/5/27 14:10
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/27 14:10
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    private List<CartItemVo> getCartListFromRedis(String cartKey) {
        // 获取的临时购物车所有的数据
        BoundHashOperations<String,Long,Object> boundHashOperations = redisTemplate.boundHashOps(cartKey);
        // 获取所有的值
        List<Object> values = boundHashOperations.values();
        // 判断这个值是否存在
        if(values!=null && values.size()>0){
            List<CartItemVo> collectList = values.stream().map(obj -> {
                // 把字符串数据转换成对象
                String cartItemJson = (String)obj;
                CartItemVo cartItemVo = JSON.parseObject(cartItemJson, CartItemVo.class);
                return cartItemVo;

            }).collect(Collectors.toList());

            return collectList;
        }
        return null;

    }

    /**
     * @Description: 获取购物车数据
     * @Author: hubin
     * @CreateDate: 2021/5/26 17:06
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/26 17:06
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    public BoundHashOperations<String,Long,Object> getCartFromRedis() {

        // 1、获取购物车中已经存在的数据
        UserInfoDTO userInfoDTO = CartInterceptor.dtoThreadLocal.get();
        String cartKey = "";
        // 判断是否登录
        if(userInfoDTO.getUserId()!=null){
            cartKey = Constants.CART_PREFIX + userInfoDTO.getUserId();
        }else{
            cartKey = Constants.CART_PREFIX + userInfoDTO.getUserKey();
        }
        // 从redis中获取购物车相关的数据
        BoundHashOperations<String,Long,Object> cartOps = redisTemplate.boundHashOps(cartKey);

        return cartOps;

    }
}

