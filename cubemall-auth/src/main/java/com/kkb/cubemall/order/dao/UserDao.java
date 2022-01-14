package com.kkb.cubemall.order.dao;

import com.kkb.cubemall.order.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-18 11:22:48
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
}
