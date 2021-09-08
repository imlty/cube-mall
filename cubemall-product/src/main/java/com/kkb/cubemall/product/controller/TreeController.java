package com.kkb.cubemall.product.controller;

import com.kkb.common.utils.PageUtils;
import com.kkb.common.utils.R;
import com.kkb.cubemall.product.entity.TreeEntity;
import com.kkb.cubemall.product.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author imlty
 * @email imlty626@qq.com
 * @date 2021-09-08 01:34:38
 */
@RestController
@RequestMapping("product/tree")
public class TreeController {
    @Autowired
    private TreeService treeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:tree:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = treeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{nodeId}")
    //@RequiresPermissions("product:tree:info")
    public R info(@PathVariable("nodeId") Integer nodeId){
		TreeEntity tree = treeService.getById(nodeId);

        return R.ok().put("tree", tree);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:tree:save")
    public R save(@RequestBody TreeEntity tree){
		treeService.save(tree);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:tree:update")
    public R update(@RequestBody TreeEntity tree){
		treeService.updateById(tree);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:tree:delete")
    public R delete(@RequestBody Integer[] nodeIds){
		treeService.removeByIds(Arrays.asList(nodeIds));

        return R.ok();
    }

}
