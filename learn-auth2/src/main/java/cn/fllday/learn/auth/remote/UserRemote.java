package cn.fllday.learn.auth.remote;

import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.pojo.user.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: gssznb
 * 远程调用 User微服务端接口
 *
 */
@FeignClient(value = "learn-user")
public interface UserRemote {

    /**
     * 根据 username 获取 username 详情
     * @param username
     * @return
     */
    @GetMapping(value = "/user/loadUserByUsername")
    AjaxResult<SysUser> getUserByUsername(@RequestParam(value = "username") String username);

    /**
     * 通过手机号码获取用户
     * @param phone
     * @return
     */
    @GetMapping(value = "/user/loadUserByPhone")
    AjaxResult<SysUser> getUserByPhone(@RequestParam(value = "phone") String phone);

    /**
     * 获取 用户权限
     * @param id
     * @return
     */
    @GetMapping(value = "/user/getUserPerms")
    AjaxResult<List<String>> getUserPerms(@RequestParam(value = "id") Long id);

}
