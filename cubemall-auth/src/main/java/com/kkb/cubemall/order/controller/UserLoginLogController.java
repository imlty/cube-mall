package com.kkb.cubemall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.cubemall.order.entity.UserLoginLogEntity;
import com.kkb.cubemall.order.service.UserLoginLogService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;



/**
 * 会员登录记录
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-18 11:22:48
 */
@RestController
@RequestMapping("user/userloginlog")
public class UserLoginLogController {
    @Autowired
    private UserLoginLogService userLoginLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:userloginlog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userLoginLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:userloginlog:info")
    public R info(@PathVariable("id") Long id){
		UserLoginLogEntity userLoginLog = userLoginLogService.getById(id);

        return R.ok().put("userLoginLog", userLoginLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:userloginlog:save")
    public R save(@RequestBody UserLoginLogEntity userLoginLog){
		userLoginLogService.save(userLoginLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:userloginlog:update")
    public R update(@RequestBody UserLoginLogEntity userLoginLog){
		userLoginLogService.updateById(userLoginLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:userloginlog:delete")
    public R delete(@RequestBody Long[] ids){
		userLoginLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
