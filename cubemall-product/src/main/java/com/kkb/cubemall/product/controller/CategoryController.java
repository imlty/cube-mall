package com.kkb.cubemall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.cubemall.product.entity.CategoryEntity;
import com.kkb.cubemall.product.service.CategoryService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;



/**
 * 商品类目
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-06 10:32:36
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;



    /**
     * 树状展示
     */
    @RequestMapping("/tree")
    public R tree(){
        List<CategoryEntity> tree = categoryService.listTree();

        return R.ok().put("data", tree);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     *根据id获取分类信息，先查询节点已有信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		CategoryEntity category = categoryService.getById(id);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
//		categoryService.removeByIds(Arrays.asList(ids));
        categoryService.removeNodesByIds(Arrays.asList(ids));
        return R.ok();
    }

}
