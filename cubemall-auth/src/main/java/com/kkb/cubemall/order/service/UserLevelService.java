package com.kkb.cubemall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.order.entity.UserLevelEntity;

import java.util.Map;

/**
 * 会员等级
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-18 11:22:48
 */
public interface UserLevelService extends IService<UserLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

