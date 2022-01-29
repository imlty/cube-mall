package com.kkb.cubemall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.user.entity.UserStatisticsInfoEntity;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-18 11:22:47
 */
public interface UserStatisticsInfoService extends IService<UserStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

