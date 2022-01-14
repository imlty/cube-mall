package com.kkb.cubemall.order.feign;

import com.kkb.cubemall.order.web.vo.UserReceiveAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName AuthRemoteClient
 * @Description
 * @Author hubin
 * @Date 2021/5/26 14:22
 * @Version V1.0
 **/
@FeignClient("cubemall-auth")
public interface AuthRemoteClient {

    /**
     * @Description: 根据token查询用户信息
     * @Author: hubin
     * @CreateDate: 2021/5/26 14:23
     * @UpdateUser: hubin
     * @UpdateDate: 2021/5/26 14:23
     * @UpdateRemark: 修改内容
     * @Version: 1.0
     */
    @RequestMapping("/user/info/{token}")
    public String userInfoByToken(@PathVariable("token") String token);

    /*
   * 查询用户地址信息：根据用户id
   * */
    @RequestMapping("/user/userreceiveaddress/address/{userId}")
    public List<UserReceiveAddressVo> addressList(@PathVariable("userId") Long uerId);


}

