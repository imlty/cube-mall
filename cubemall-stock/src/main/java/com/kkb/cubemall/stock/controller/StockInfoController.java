package com.kkb.cubemall.stock.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.cubemall.stock.entity.StockInfoEntity;
import com.kkb.cubemall.stock.service.StockInfoService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;



/**
 * 仓库信息
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-06-01 15:14:10
 */
@RestController
@RequestMapping("user/stockinfo")
public class StockInfoController {
    @Autowired
    private StockInfoService stockInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:stockinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stockInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:stockinfo:info")
    public R info(@PathVariable("id") Long id){
		StockInfoEntity stockInfo = stockInfoService.getById(id);

        return R.ok().put("stockInfo", stockInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:stockinfo:save")
    public R save(@RequestBody StockInfoEntity stockInfo){
		stockInfoService.save(stockInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:stockinfo:update")
    public R update(@RequestBody StockInfoEntity stockInfo){
		stockInfoService.updateById(stockInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:stockinfo:delete")
    public R delete(@RequestBody Long[] ids){
		stockInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
