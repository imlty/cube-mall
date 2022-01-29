package com.kkb.cubemall.product.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.cubemall.product.entity.SkuInfoEntity;
import com.kkb.cubemall.product.service.SkuInfoService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;



/**
 * sku
 *
 * @author jiaoshou
 * @email seaizon@gmail.com
 * @date 2021-04-13 20:26:25
 */
@RestController
@RequestMapping("product/skuinfo")
public class SkuInfoController {
    @Autowired
    private SkuInfoService skuInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:skuinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = skuInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:skuinfo:info")
    public R info(@PathVariable("id") Long id){
		SkuInfoEntity skuInfo = skuInfoService.getById(id);

        return R.ok().put("skuInfo", skuInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SkuInfoEntity skuInfo){
		skuInfoService.save(skuInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SkuInfoEntity skuInfo){
		skuInfoService.updateById(skuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		skuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * @Description: 根据skuID查询商品的最新价格信息
     * @Author: hubin
     * @CreateDate: 2021/5/31 15:55
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/31 15:55
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @RequestMapping("/price/{skuId}")
    public BigDecimal getPrice(@PathVariable("skuId") Long skuId){
        SkuInfoEntity skuInfoEntity = skuInfoService.getById(skuId);
        // 获取最新价格信息
        return skuInfoEntity.getPrice();
    }

}
