package com.kkb.cubemall.seckill.controller;

import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.seckill.service.SeckillService;
import com.kkb.cubemall.seckill.to.SeckillSkuRedisTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
public class SeckillController {

    @Autowired
    private SeckillService seckillService;


    @GetMapping(value = "/getCurrentSeckillSuks")
    @ResponseBody
    public R getCurrentSeckillSkus(){
        log.info("getCurrentSeckillSuks:"+ LocalDateTime.now());
        List<SeckillSkuRedisTo> vos = seckillService.getCurrentSeckillSuks();
        return R.ok().setData(vos);
    }

    @GetMapping(value = "/uploadSeckillSkuLatest3Days")
    @ResponseBody
    public R uploadSeckillSkus(){
        seckillService.uploadSeckillSkuLateset3Days();
        return R.ok();
    }

    @GetMapping(value = "/sku/seckill/{skuId}")
    @ResponseBody
    public R getSeckillSkuRedisTo(@PathVariable("skuId") Long skuId){

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SeckillSkuRedisTo vos = seckillService.getSeckillSkuRedisTo(skuId);
        return R.ok().setData(vos);
    }

    @GetMapping(value = "/kill")
    public String seckill(@RequestParam("killId") String killId,
                          @RequestParam("key") String key,
                          @RequestParam("num") Integer num,
                          Model model){

        String orderSn = seckillService.kill(killId,key,num);
        model.addAttribute("orderSn",orderSn);
        return "success";
    }


}
