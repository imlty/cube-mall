package com.kkb.cubemall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.cubemall.order.entity.OrderReturnEntity;
import com.kkb.cubemall.order.service.OrderReturnService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;



/**
 * 订单退货申请
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-28 18:50:53
 */
@RestController
@RequestMapping("user/orderreturn")
public class OrderReturnController {
    @Autowired
    private OrderReturnService orderReturnService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:orderreturn:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderReturnService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:orderreturn:info")
    public R info(@PathVariable("id") Long id){
		OrderReturnEntity orderReturn = orderReturnService.getById(id);

        return R.ok().put("orderReturn", orderReturn);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:orderreturn:save")
    public R save(@RequestBody OrderReturnEntity orderReturn){
		orderReturnService.save(orderReturn);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:orderreturn:update")
    public R update(@RequestBody OrderReturnEntity orderReturn){
		orderReturnService.updateById(orderReturn);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:orderreturn:delete")
    public R delete(@RequestBody Long[] ids){
		orderReturnService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
