package com.kkb.cubemall.product.web;

import com.kkb.cubemall.product.service.SkuInfoService;
import com.kkb.cubemall.product.vo.SkuItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.PipedReader;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName SkuItemController
 * @Description
 * @Author hubin
 * @Date 2021/5/15 18:13
 * @Version V1.0
 **/
@Slf4j
@Controller
public class SkuItemController {

    @Autowired
    private SkuInfoService skuInfoService;
    /**
     * @Description: 根据skuId查询商品详情信息
     * @Author: hubin
     * @CreateDate: 2021/5/15 18:14
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/15 18:14
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable Long skuId, Model model) throws ExecutionException, InterruptedException {
        // 调用服务层商品详情接口
        SkuItemVo itemVo = skuInfoService.skuItem(skuId);

        // 输入日志
        log.info("商品详情接口，查询的数据：{}",itemVo);


        // 把数据放入模型驱动
        model.addAttribute("item",itemVo);

        // 返回视图页面，做视图数据渲染
        return "item";
    }


}

