package com.kkb.cubemall.order.web;

import com.alibaba.fastjson.JSON;
import com.kkb.cubemall.order.config.OAuth2Properties;
import com.kkb.cubemall.order.entity.UserEntity;
import com.kkb.cubemall.order.pojo.vo.OAuthUser;
import com.kkb.cubemall.order.utils.Constants;
import com.kkb.cubemall.order.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName OAuth2Controller
 * @Description
 * @Author hubin
 * @Date 2021/5/24 18:00
 * @Version V1.0
 **/
@Slf4j
@Controller
public class OAuth2Controller {

    // 注入属性地址对象
    @Autowired
    private OAuth2Properties auth2Properties;

    // 注入restTemplate
    @Autowired
    private RestTemplate restTemplate;

    // 注入redis模板对象
    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @GetMapping("/oauth/authorize")
    public String authorize(){
        String url = auth2Properties.getAuthorizationUrl() +
                "?client_id="+auth2Properties.getClientId() +
                "&redirect_uri="+auth2Properties.getRedirectUrl();
        log.info("授权url:{}",url);
        // 重定向到授权地址
        return "redirect:"+url;
    }

    /**
     * @Description: 根据回调地址调用第三方网站授权认证接口
     * @Author: hubin
     * @CreateDate: 2021/5/24 18:28
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/24 18:28
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @GetMapping("/auth2/success")
    public String callback(@RequestParam("code") String code,
                           Model model,
                           HttpServletRequest request,
                           HttpServletResponse response){

        // 获取access_token
        //https://github.com/login/oauth/access_token?client_id...
        // 组装参数
        String url = auth2Properties.getAccessTokenUrl()+
                "?client_id="+auth2Properties.getClientId()+
                "&client_secret="+auth2Properties.getClientSecret()+
                "&code="+code+
                "&grant_type=authorization_code";

        // 以上请求就是获取access_token的请求
        log.info("获取access_token请求：{}",url);

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept","application/json");

        // 构建请求响应实体对象
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        // post请求方式
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
        // 获取请求响应结果
        String result = responseEntity.getBody();
        //
        log.info("远程请求github授权地址，获取access_token:{}",result);

        //解析响应结果
        Map<String,String> maps = JSON.parseObject(result,Map.class);
        // 获取access_token
        String access_token = maps.get("access_token");

        // 使用access_token换取用户信息，实现用户登录
        OAuthUser oAuthUser = this.getUserInfo(access_token);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(oAuthUser.getName());
        userEntity.setEmail(oAuthUser.getEmail());


        // 生成一个token,唯一标识
        String token = DigestUtils.md5DigestAsHex(userEntity.getUsername().getBytes());

        // 把用户数据放入redis中
        redisTemplate.opsForValue().set(Constants.REDIS_LOGIN_KEY+token,JSON.toJSONString(userEntity));
        // 设置用户身份信息过期时间
        redisTemplate.expire(Constants.REDIS_LOGIN_KEY+token,24, TimeUnit.HOURS);


        // 把token写入cookie
        CookieUtils.setCookie(request,
                response,
                Constants.COOKIE_LOGIN_KEY,
                token,
                86400,
                true);

        // 重定向到
        return "redirect:http://localhost:8081/15.html";

    }

    /**
     * @Description: 根据access_token获取用户信息
     * @Author: hubin
     * @CreateDate: 2021/5/24 19:03
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/24 19:03
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    private OAuthUser getUserInfo(String access_token) {

        // 获取请求地址
        String url = auth2Properties.getUserInfoUrl();

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept","application/json");
        // 把access_token放入请求头
        headers.add("Authorization","token "+access_token);
        // 构建请求响应实体对象
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        // get请求方式
        ResponseEntity<String> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET,
                httpEntity,
                String.class);
        // 获取请求响应结果
        String result = responseEntity.getBody();

        // 把json字符串转换为对象
        OAuthUser oAuthUser = JSON.parseObject(result, OAuthUser.class);
        return oAuthUser;
    }


}

