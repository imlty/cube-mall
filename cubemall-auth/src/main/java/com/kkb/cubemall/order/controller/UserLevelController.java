package com.kkb.cubemall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.cubemall.order.entity.UserLevelEntity;
import com.kkb.cubemall.order.service.UserLevelService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;



/**
 * 会员等级
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-18 11:22:48
 */
@RestController
@RequestMapping("user/userlevel")
public class UserLevelController {
    @Autowired
    private UserLevelService userLevelService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:userlevel:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userLevelService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:userlevel:info")
    public R info(@PathVariable("id") Long id){
		UserLevelEntity userLevel = userLevelService.getById(id);

        return R.ok().put("userLevel", userLevel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:userlevel:save")
    public R save(@RequestBody UserLevelEntity userLevel){
		userLevelService.save(userLevel);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:userlevel:update")
    public R update(@RequestBody UserLevelEntity userLevel){
		userLevelService.updateById(userLevel);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:userlevel:delete")
    public R delete(@RequestBody Long[] ids){
		userLevelService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
