package com.kkb.cubemall.product.exception;

import com.kkb.cubemall.common.exception.CubemallEnum;
import com.kkb.cubemall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller层 统一异常处理
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.kkb.cubemall.product.controller")
public class CubemallExceptionControllerAdvice {

    //如果能够精确匹配到该异常就会执行这个方法,后续执行下面的方法
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleVaildException(MethodArgumentNotValidException e){
        log.error("数据校验出现问题{}, 异常类型{}", e.getMessage(), e.getCause());

        //1.获取校验的错误结果
        BindingResult result = e.getBindingResult();
        Map<String, String> map = new HashMap<>();

        result.getFieldErrors().forEach((fieldError) -> {
            //获取每个错误的属性的名字
            String field = fieldError.getField();
            //获取每个错误属性的提示
            String message = fieldError.getDefaultMessage();

            map.put(field, message);
        });
        return R.error(CubemallEnum.VAILD_EXCEPTION.getCode(), CubemallEnum.VAILD_EXCEPTION.getMsg()).put("data", map);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleVaildException(Throwable throwable){
        log.error("错误", throwable);
        return R.error(CubemallEnum.UNKNOW_EXCEPTION.getCode(), CubemallEnum.UNKNOW_EXCEPTION.getMsg()).put("data", "未知异常");
    }
}
