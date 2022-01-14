package com.kkb.cubemall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.cubemall.order.entity.UserCollectSubjectEntity;
import com.kkb.cubemall.order.service.UserCollectSubjectService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;



/**
 * 会员收藏的专题活动
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-18 11:22:48
 */
@RestController
@RequestMapping("user/usercollectsubject")
public class UserCollectSubjectController {
    @Autowired
    private UserCollectSubjectService userCollectSubjectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:usercollectsubject:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userCollectSubjectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:usercollectsubject:info")
    public R info(@PathVariable("id") Long id){
		UserCollectSubjectEntity userCollectSubject = userCollectSubjectService.getById(id);

        return R.ok().put("userCollectSubject", userCollectSubject);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:usercollectsubject:save")
    public R save(@RequestBody UserCollectSubjectEntity userCollectSubject){
		userCollectSubjectService.save(userCollectSubject);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:usercollectsubject:update")
    public R update(@RequestBody UserCollectSubjectEntity userCollectSubject){
		userCollectSubjectService.updateById(userCollectSubject);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:usercollectsubject:delete")
    public R delete(@RequestBody Long[] ids){
		userCollectSubjectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
