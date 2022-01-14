package com.kkb.cubemall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.order.entity.UserLoginLogEntity;

import java.util.Map;

/**
 * 会员登录记录
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-18 11:22:48
 */
public interface UserLoginLogService extends IService<UserLoginLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

