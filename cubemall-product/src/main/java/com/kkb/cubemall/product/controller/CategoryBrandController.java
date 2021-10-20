package com.kkb.cubemall.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.product.entity.CategoryBrandEntity;
import com.kkb.cubemall.product.service.BrandService;
import com.kkb.cubemall.product.service.CategoryBrandService;
import com.kkb.cubemall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 分类品牌关系表
 *
 * @author peige
 * @email peige@gmail.com
 * @date 2021-04-22 11:03:03
 */
@RestController
@RequestMapping("product/categorybrand")
public class CategoryBrandController {
    @Autowired
    private CategoryBrandService categoryBrandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @RequestMapping("/category/list")
    //@RequiresPermissions("product:categorybrand:list")
    public R categoryList(@RequestParam("brandId") Long brand_id){

        // 查询关联表
        List<CategoryBrandEntity> data = categoryBrandService.list(
                new QueryWrapper<CategoryBrandEntity>().eq("brand_id", brand_id)
        );

        // 通过关联表，查询 category
        data.forEach(entity ->{
            entity.setBrandName(brandService.getById(entity.getBrandId()).getName());
            entity.setCategoryName(categoryService.getById(entity.getCategoryId()).getName());
        });
        return R.ok().put("data", data);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:categorybrand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:categorybrand:info")
    public R info(@PathVariable("id") Integer id){
		CategoryBrandEntity categoryBrand = categoryBrandService.getById(id);

        return R.ok().put("categoryBrand", categoryBrand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:categorybrand:save")
    public R save(@RequestBody CategoryBrandEntity categoryBrand){
		categoryBrandService.save(categoryBrand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:categorybrand:update")
    public R update(@RequestBody CategoryBrandEntity categoryBrand){
		categoryBrandService.updateById(categoryBrand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:categorybrand:delete")
    public R delete(@RequestBody CategoryBrandEntity categoryBrand){

        categoryBrandService.remove(
                new QueryWrapper<CategoryBrandEntity>()
                        .eq("brand_id",categoryBrand.getBrandId())
                        .eq("category_id",categoryBrand.getCategoryId())
        );
        return R.ok();
    }

}
