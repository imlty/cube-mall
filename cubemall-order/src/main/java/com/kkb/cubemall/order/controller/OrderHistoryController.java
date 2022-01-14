package com.kkb.cubemall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.cubemall.order.entity.OrderHistoryEntity;
import com.kkb.cubemall.order.service.OrderHistoryService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;



/**
 * 订单操作历史记录
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-28 18:50:53
 */
@RestController
@RequestMapping("user/orderhistory")
public class OrderHistoryController {
    @Autowired
    private OrderHistoryService orderHistoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:orderhistory:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderHistoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:orderhistory:info")
    public R info(@PathVariable("id") Long id){
		OrderHistoryEntity orderHistory = orderHistoryService.getById(id);

        return R.ok().put("orderHistory", orderHistory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:orderhistory:save")
    public R save(@RequestBody OrderHistoryEntity orderHistory){
		orderHistoryService.save(orderHistory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:orderhistory:update")
    public R update(@RequestBody OrderHistoryEntity orderHistory){
		orderHistoryService.updateById(orderHistory);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:orderhistory:delete")
    public R delete(@RequestBody Long[] ids){
		orderHistoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
