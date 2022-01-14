package com.kkb.cubemall.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.kkb.cubemall.common.utils.R;
import com.kkb.cubemall.order.pojo.UserDTO;
import com.kkb.cubemall.order.utils.Constants;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.order.dao.UserDao;
import com.kkb.cubemall.order.entity.UserEntity;
import com.kkb.cubemall.order.service.UserService;
import org.springframework.util.DigestUtils;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {


    // 注入redis服务
    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * @Description: 登录认证的接口实现
     * @Author: hubin
     * @CreateDate: 2021/5/18 16:44
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/18 16:44
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @Override
    public R login(UserDTO userDTO) {

        String encode = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        System.out.println("加密："+encode);

        // 1、根据用户名查询数据库，查看用户名是否匹配
        UserEntity userEntity = this.getOne(new QueryWrapper<UserEntity>().eq("username", userDTO.getUsername()));
        // 判断用户是否存在
        if(userEntity!=null){
            // 2、匹配用户的密码
            boolean matches = new BCryptPasswordEncoder().matches(userDTO.getPassword(), userEntity.getPassword());
            // 判断密码是否匹配
            if(matches){
                // 如果matches=true,表示用户登录成功

                // 生成一个token,唯一标识
                String token = DigestUtils.md5DigestAsHex(userDTO.getUsername().getBytes());
                // 不能暴露密码
                userEntity.setPassword(null);
                // 需要把用户身份信息存储在redis
                redisTemplate.opsForValue().set(Constants.REDIS_LOGIN_KEY+token,JSON.toJSONString(userEntity));
                // 设置用户身份信息过期时间
                redisTemplate.expire(Constants.REDIS_LOGIN_KEY+token,24, TimeUnit.HOURS);

                // 登录成功
                return R.ok(token);
            }
        }

        // 登录失败
        return R.error();
    }

    /**
     * @Description: 根据token查询用户身份信息
     * @Author: hubin
     * @CreateDate: 2021/5/19 18:27
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/19 18:27
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     * @url : http://localhost:8082/user/info/"+token
     */
    @Override
    public R userInfo(String token) {

        // 根据token从redis服务中查询用户身份信息
        String userJson = redisTemplate.opsForValue().get(Constants.REDIS_LOGIN_KEY + token);
        // 把json字符串转换对象
        if(StringUtils.isBlank(userJson)){
            return R.error("用户身份已失效");
        }
        // 用户身份存在
        UserEntity userEntity = JSON.parseObject(userJson, UserEntity.class);

        // 返回结果
        return R.ok(userEntity);
    }


}
