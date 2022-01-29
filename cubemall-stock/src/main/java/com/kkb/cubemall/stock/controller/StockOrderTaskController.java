package com.kkb.cubemall.stock.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.cubemall.stock.entity.StockOrderTaskEntity;
import com.kkb.cubemall.stock.service.StockOrderTaskService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;



/**
 * 库存工作单
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-06-01 15:14:10
 */
@RestController
@RequestMapping("user/stockordertask")
public class StockOrderTaskController {
    @Autowired
    private StockOrderTaskService stockOrderTaskService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:stockordertask:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stockOrderTaskService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:stockordertask:info")
    public R info(@PathVariable("id") Long id){
		StockOrderTaskEntity stockOrderTask = stockOrderTaskService.getById(id);

        return R.ok().put("stockOrderTask", stockOrderTask);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:stockordertask:save")
    public R save(@RequestBody StockOrderTaskEntity stockOrderTask){
		stockOrderTaskService.save(stockOrderTask);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:stockordertask:update")
    public R update(@RequestBody StockOrderTaskEntity stockOrderTask){
		stockOrderTaskService.updateById(stockOrderTask);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:stockordertask:delete")
    public R delete(@RequestBody Long[] ids){
		stockOrderTaskService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
