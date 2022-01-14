package com.kkb.cubemall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.order.entity.UserCollectSubjectEntity;

import java.util.Map;

/**
 * 会员收藏的专题活动
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-18 11:22:48
 */
public interface UserCollectSubjectService extends IService<UserCollectSubjectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

