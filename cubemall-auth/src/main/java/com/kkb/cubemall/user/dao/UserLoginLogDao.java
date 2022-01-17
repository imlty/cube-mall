package com.kkb.cubemall.user.dao;

import com.kkb.cubemall.user.entity.UserLoginLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员登录记录
 * 
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-18 11:22:48
 */
@Mapper
public interface UserLoginLogDao extends BaseMapper<UserLoginLogEntity> {
	
}
