package com.kkb.cubemall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.cubemall.order.entity.UserStatisticsInfoEntity;
import com.kkb.cubemall.order.service.UserStatisticsInfoService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;



/**
 * 会员统计信息
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-18 11:22:47
 */
@RestController
@RequestMapping("user/userstatisticsinfo")
public class UserStatisticsInfoController {
    @Autowired
    private UserStatisticsInfoService userStatisticsInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:userstatisticsinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userStatisticsInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:userstatisticsinfo:info")
    public R info(@PathVariable("id") Long id){
		UserStatisticsInfoEntity userStatisticsInfo = userStatisticsInfoService.getById(id);

        return R.ok().put("userStatisticsInfo", userStatisticsInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:userstatisticsinfo:save")
    public R save(@RequestBody UserStatisticsInfoEntity userStatisticsInfo){
		userStatisticsInfoService.save(userStatisticsInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:userstatisticsinfo:update")
    public R update(@RequestBody UserStatisticsInfoEntity userStatisticsInfo){
		userStatisticsInfoService.updateById(userStatisticsInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:userstatisticsinfo:delete")
    public R delete(@RequestBody Long[] ids){
		userStatisticsInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
