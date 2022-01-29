package com.kkb.cubemall.stock.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.cubemall.stock.entity.StockSkuEntity;
import com.kkb.cubemall.stock.service.StockSkuService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;



/**
 * 商品库存
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-06-01 15:14:09
 */
@RestController
@RequestMapping("user/stocksku")
public class StockSkuController {
    @Autowired
    private StockSkuService stockSkuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:stocksku:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stockSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:stocksku:info")
    public R info(@PathVariable("id") Long id){
		StockSkuEntity stockSku = stockSkuService.getById(id);

        return R.ok().put("stockSku", stockSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:stocksku:save")
    public R save(@RequestBody StockSkuEntity stockSku){
		stockSkuService.save(stockSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:stocksku:update")
    public R update(@RequestBody StockSkuEntity stockSku){
		stockSkuService.updateById(stockSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:stocksku:delete")
    public R delete(@RequestBody Long[] ids){
		stockSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
