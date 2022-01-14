package com.kkb.cubemall.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.order.entity.UserEntity;
import com.kkb.cubemall.order.feign.AuthRemoteClient;
import com.kkb.cubemall.order.feign.CartRemoteClient;
import com.kkb.cubemall.order.feign.StockRemoteClient;
import com.kkb.cubemall.order.interceptor.OrderInterceptor;
import com.kkb.cubemall.order.utils.Constants;
import com.kkb.cubemall.order.web.vo.CartItemVo;
import com.kkb.cubemall.order.web.vo.OrderConfirmVo;
import com.kkb.cubemall.order.web.vo.StockSkuVo;
import com.kkb.cubemall.order.web.vo.UserReceiveAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.order.dao.OrderDao;
import com.kkb.cubemall.order.entity.OrderEntity;
import com.kkb.cubemall.order.service.OrderService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {


    // 注入线程池对象
    @Autowired
    private ThreadPoolExecutor poolExecutor;

    // 注入远程调用地址接口
    @Autowired
    private AuthRemoteClient authRemoteClient;

    // 注入购物车远程调用接口
    @Autowired
    private CartRemoteClient cartRemoteClient;

    // 注入库存服务
    @Autowired
    private StockRemoteClient stockRemoteClient;


    // 注入redis模板服务
    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * @Description: 查询订单结算页相关数据
     *
     * 1、地址收货信息
     * 2、购物清单
     * @Author: hubin
     * @CreateDate: 2021/5/31 15:02
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/31 15:02
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @Override
    public OrderConfirmVo confirmOrder() {

        // 创建订单结算也视图对象
        OrderConfirmVo confirmVo = new OrderConfirmVo();

        // 获取请求上下文信息
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();


        // 1、 查询用户地址信息
        // 获取当前用户信息
        UserEntity userInfo = OrderInterceptor.dtoThreadLocal.get();
        // 根据用户id查询用户信息
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {

            // 异步请求之前，共享请求数据
            RequestContextHolder.setRequestAttributes(requestAttributes);

            // 远程调用用户授权认证，用户中心服务，查询用户数据
            List<UserReceiveAddressVo> userReceiveAddressVos = authRemoteClient.addressList(userInfo.getId());
            // 把用户地址信息添加到订单结算实体对象
            confirmVo.setUserReceiveAddressList(userReceiveAddressVos);
        }, poolExecutor);

        // 2、查询购物车清单数据
        CompletableFuture<Void> cartItemsFuture = CompletableFuture.runAsync(() -> {

            // 异步请求之前，共享请求数据
            RequestContextHolder.setRequestAttributes(requestAttributes);

            // 远程调用购物车服务方法，查询购物车清单
            List<CartItemVo> cartItems = cartRemoteClient.getCartItems();
            confirmVo.setItems(cartItems);
        }, poolExecutor).thenRunAsync(()->{
            // 获取购物车清单数据
            List<CartItemVo> items = confirmVo.getItems();

            // 获取所有的skuIds
            List<Long> skuIds = items.stream().map(itemVo -> itemVo.getSkuId()).collect(Collectors.toList());
            // 根据商品skuID查询每一个商品的库存信息
            // 调用库存服务
            R skuStock = stockRemoteClient.getSkuStock(skuIds);

            // 获取是否具有库存数据
            List<StockSkuVo> skuStockDataList = skuStock.getData("data", new TypeReference<List<StockSkuVo>>() {
            });

            // 把是否具有库存的数据转换为map结构数据
            if(skuStockDataList!=null && skuStockDataList.size()>0){
                Map<Long, Boolean> hasStock = skuStockDataList.stream().collect(Collectors.toMap(StockSkuVo::getSkuId, StockSkuVo::getHasStock));
                // 添加对象
                confirmVo.setStocks(hasStock);
            }

        },poolExecutor);


        // 3、 获取用户优惠券信息
        Integer integration = userInfo.getIntegration();
        confirmVo.setIntegration(integration);

        //4、获取商品库存信息，判断商品是否具有库存

        // 防重令牌
        // 由于网络延迟（重试），订单提交按钮可能多次提交
        // 防重令牌，提交订单时携带此令牌
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(Constants.ORDER_TOKEN_PREFIX+userInfo.getId(),token,30, TimeUnit.MINUTES);
        // 放入令牌对象属性
        confirmVo.setOrderToken(token);

        //同步调用
        try {
            CompletableFuture.allOf(addressFuture,cartItemsFuture).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return confirmVo;
    }

}