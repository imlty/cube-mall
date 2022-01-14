package com.kkb.cubemall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kkb.cubemall.common.utils.PageUtils;
import com.kkb.cubemall.order.entity.UserReceiveAddressEntity;

import java.util.List;
import java.util.Map;

/**
 * 会员收货地址
 *
 * @author ithubin
 * @email ithubin@gmail.com
 * @date 2021-05-18 11:22:47
 */
public interface UserReceiveAddressService extends IService<UserReceiveAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * @Description: 查询地址列表数据
     * @Author: hubin
     * @CreateDate: 2021/5/31 15:33
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/31 15:33
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    List<UserReceiveAddressEntity> addressList(Long userId);
}

