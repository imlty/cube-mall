package com.kkb.cubemall.product.controller;

import java.util.*;


import com.kkb.cubemall.common.valid.AddGroup;
import com.kkb.cubemall.common.valid.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.cubemall.product.entity.BrandEntity;
import com.kkb.cubemall.product.service.BrandService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌表
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-07 11:38:21
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("id") Integer id){
		BrandEntity brand = brandService.getById(id);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated({AddGroup.class}) @RequestBody BrandEntity brand){


        brandService.save(brand);


        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@Validated({UpdateGroup.class}) @RequestBody BrandEntity brand){
		brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Integer[] ids){
		brandService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 信息
     */
    @RequestMapping("/oom/{id}")
    //@RequiresPermissions("product:brand:info")
    public void oom(@PathVariable("id") Integer id){

        List<BrandEntity> brandEntityList = new ArrayList<>();

        while (true){
            brandEntityList.add(new BrandEntity());
        }
    }

}
