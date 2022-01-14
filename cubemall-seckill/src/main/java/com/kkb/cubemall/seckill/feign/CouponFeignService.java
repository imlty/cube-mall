package com.kkb.cubemall.seckill.feign;

import com.kkb.cubemall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("cubemall-coupon")
public interface CouponFeignService {

    @GetMapping(value = "/coupon/seckillsession/late3DaySession")
    R getLates3DaySession();
}
