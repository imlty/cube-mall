package com.kkb.cubemall.order.feign;

import com.kkb.cubemall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName StockRemoteClient
 * @Description
 * @Author hubin
 * @Date 2021/6/1 15:21
 * @Version V1.0
 **/
@FeignClient("cubemall-stock")
public interface StockRemoteClient {

    /**
     * @Description: 根据skuid查询库存信息
     * @Author: hubin
     * @CreateDate: 2021/6/1 15:25
     * @UpdateUser: hubin
     * @UpdateDate: 2021/6/1 15:25
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @RequestMapping("/stock/sku/hasStock")
    public R getSkuStock(@RequestBody List<Long> skuIds);


}
