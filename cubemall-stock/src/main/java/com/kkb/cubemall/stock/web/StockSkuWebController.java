package com.kkb.cubemall.stock.web;

import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.stock.service.StockSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName StockSkuWebController
 * @Description
 * @Author hubin
 * @Date 2021/6/1 15:35
 * @Version V1.0
 **/
@RestController
public class StockSkuWebController {


    // 注入service服务对象
    @Autowired
    private StockSkuService stockSkuService;

    /**
     * @Description: 根据skuID查询数据信息
     * @Author: hubin
     * @CreateDate: 2021/6/1 15:37
     * @UpdateUser: hubin
     * @UpdateDate: 2021/6/1 15:37
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @RequestMapping("/stock/sku/hasStock")
    public R getSkuStock(@RequestBody List<Long> skuIds){
       R r = stockSkuService.getSkuStock(skuIds);
       return r;
    }
}

