package com.kkb.cubemall.stock.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kkb.cubemall.stock.entity.StockOrderTaskDetailEntity;
import com.kkb.cubemall.stock.service.StockOrderTaskDetailService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.R;



/**
 * 库存工作单
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-06-01 15:14:09
 */
@RestController
@RequestMapping("user/stockordertaskdetail")
public class StockOrderTaskDetailController {
    @Autowired
    private StockOrderTaskDetailService stockOrderTaskDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:stockordertaskdetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stockOrderTaskDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:stockordertaskdetail:info")
    public R info(@PathVariable("id") Long id){
		StockOrderTaskDetailEntity stockOrderTaskDetail = stockOrderTaskDetailService.getById(id);

        return R.ok().put("stockOrderTaskDetail", stockOrderTaskDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:stockordertaskdetail:save")
    public R save(@RequestBody StockOrderTaskDetailEntity stockOrderTaskDetail){
		stockOrderTaskDetailService.save(stockOrderTaskDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:stockordertaskdetail:update")
    public R update(@RequestBody StockOrderTaskDetailEntity stockOrderTaskDetail){
		stockOrderTaskDetailService.updateById(stockOrderTaskDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:stockordertaskdetail:delete")
    public R delete(@RequestBody Long[] ids){
		stockOrderTaskDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
