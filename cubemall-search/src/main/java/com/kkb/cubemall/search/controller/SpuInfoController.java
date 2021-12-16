package com.kkb.cubemall.search.controller;

import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.search.service.SpuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spuInfo")
public class SpuInfoController {

    @Autowired
    private SpuInfoService spuInfoService;
    private volatile boolean executeFlag = true;

    @GetMapping("/putonsale/{spuId}")
    public R putOnSale(@PathVariable("spuId") Long spuId) {
        spuInfoService.putOnSale(spuId);
        return R.ok();
    }

    /**
     * 商品数据全量同步
     * @return
     */
    @GetMapping("/syncSpuInfo")
    public R putOnSale() {
        if (executeFlag){
            synchronized (this){
                if (executeFlag){
                    try {
                        executeFlag = false;
                        spuInfoService.syncSpuInfo();
                    } finally {
                        executeFlag = true;
                    }
                    return R.ok("数据正在导入成功！");
                }
            }
        }
        return R.ok("数据正在导入中，请勿重复执行！");
    }
}
