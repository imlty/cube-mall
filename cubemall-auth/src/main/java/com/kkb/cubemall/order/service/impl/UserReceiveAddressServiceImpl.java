package com.kkb.cubemall.order.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.common.utils.Query;

import com.kkb.cubemall.order.dao.UserReceiveAddressDao;
import com.kkb.cubemall.order.entity.UserReceiveAddressEntity;
import com.kkb.cubemall.order.service.UserReceiveAddressService;


@Service("userReceiveAddressService")
public class UserReceiveAddressServiceImpl extends ServiceImpl<UserReceiveAddressDao, UserReceiveAddressEntity> implements UserReceiveAddressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserReceiveAddressEntity> page = this.page(
                new Query<UserReceiveAddressEntity>().getPage(params),
                new QueryWrapper<UserReceiveAddressEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * @Description: 查询地址列表数据
     * @Author: hubin
     * @CreateDate: 2021/5/31 15:33
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/31 15:33
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @Override
    public List<UserReceiveAddressEntity> addressList(Long userId) {

        List<UserReceiveAddressEntity> addressEntityList = this.baseMapper.selectList(new QueryWrapper<UserReceiveAddressEntity>().eq("user_id", userId));

        return addressEntityList;
    }

}