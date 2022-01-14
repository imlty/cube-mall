package com.kkb.cubemall.order.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.order.config.SpringBeansUtils;
import com.kkb.cubemall.order.entity.UserEntity;
import com.kkb.cubemall.order.feign.AuthRemoteClient;
import com.kkb.cubemall.order.utils.Constants;
import com.kkb.cubemall.order.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName OrderInterceptor
 * @Description
 * @Author hubin
 * @Date 2021/5/26 14:13
 * @Version V1.0
 **/
public class OrderInterceptor implements HandlerInterceptor{


    // 创建一个ThreadLocal对象，用来存储用户身份信息
    // 主要用来实现数据隔离，填充的数据只属于当前线程，高并发用户情况下，每一个用户只维护自己的用户信息即可
    public static ThreadLocal<UserEntity> dtoThreadLocal = new ThreadLocal<>();

    /**
     * @Description: 业务方法执行之前执行的方法
     * @Author: hubin
     * @CreateDate: 2021/5/26 14:15
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/26 14:15
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 注入远程调用rpc接口
        AuthRemoteClient remoteClient = (AuthRemoteClient) SpringBeansUtils.getBeanClass(AuthRemoteClient.class);

        // 获取cookie中token，判断token是否存在，如果token不存在，表示用户没有登录
        String token = CookieUtils.getCookieValue(request, Constants.COOKIE_LOGIN_KEY, true);
        if(StringUtils.isNotBlank(token)){
            // 根据接口远程调用认证服务，获取用户身份信息
            String jsonUser = remoteClient.userInfoByToken(token);

            if(StringUtils.isNotBlank(jsonUser)){

                // 转换对象
                R r = JSON.parseObject(jsonUser, R.class);
                // 判断
                if(r.getCode() == 0){

                    // 获取用户身份信息对象
                    UserEntity result = r.getData("result", new TypeReference<UserEntity>() {
                    });
                    // 使用ThreadLocal存储用户信息
                    dtoThreadLocal.set(result);
                    // 用户处于登录状态
                    return  true;
                }

            }

        }

        // 跳转到登录页面去登录
        response.sendRedirect("http://localhost:8082");
        // 用户未登录
        return false;

        }



    /**
     * @Description: 业务执行之后完成执行的方法
     * @Author: hubin
     * @CreateDate: 2021/5/26 14:15
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/26 14:15
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    /**
     * @Description: 整个任务全部完成，执行方法
     * @Author: hubin
     * @CreateDate: 2021/5/26 14:16
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/26 14:16
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}

