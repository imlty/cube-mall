package com.kkb.cubemall.product.feign.fallback;

import com.kkb.cubemall.common.exception.BizCodeEnum;
import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.product.feign.SeckillFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SeckillFeignServiceFallback implements SeckillFeignService {
    @Override
    public R getSeckillSkuRedisTo(Long skuId) {
        log.info("getSeckillSkuRedisTo熔断方法被调用");

        return R.error(BizCodeEnum.TOO_MANY_REQUEST.getCode(),BizCodeEnum.TOO_MANY_REQUEST.getMessage());
    }
}
