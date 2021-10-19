package com.kkb.cubemall.product.controller;

import com.kkb.cubemall.common.exception.CubemaillEnum;
import com.kkb.cubemall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * Controller 全局统一异常处理
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.kkb.cubemall.product.controller")
public class ProductExceptionControllerAdvice {

    // 如果能后精准匹配到该异常，就会执行这个方法，后续执行下面的方法
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleVaildException(MethodArgumentNotValidException e){
        log.error("数据校验出现问题{},异常类型{}",e.getMessage(),e.getCause());

        BindingResult result = e.getBindingResult();
        HashMap<String, String> map = new HashMap<>();
        result.getFieldErrors().forEach(fidldErr -> map.put(fidldErr.getField(),fidldErr.getDefaultMessage()));
        return R.error(CubemaillEnum.VAILD_EXCEPTION.getCode(), CubemaillEnum.VAILD_EXCEPTION.getMessage()).put("data",map);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleVaildThrowable(Throwable throwable){
        log.error("错误",throwable);
        return R.error(CubemaillEnum.UNKNOW_EXCEPTION.getCode(),CubemaillEnum.UNKNOW_EXCEPTION.getMessage());
    }
}
