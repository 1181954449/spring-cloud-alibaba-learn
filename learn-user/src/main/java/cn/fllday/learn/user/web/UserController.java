package cn.fllday.learn.user.web;

import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.common.ServiceExceptionEnum;
import cn.fllday.learn.pojo.user.SysUser;
import cn.fllday.learn.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: gssznb
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "loadUserByUsername")
    public AjaxResult<SysUser> getUserByUsername(@RequestParam String username) {
        log.info("通过用户名查询用户： [ {} ]", username);
        if (StringUtils.isEmpty(username)) {
            return AjaxResult.error(ServiceExceptionEnum.USER_NOT_FOUNT_ERROR);
        }
        SysUser bean = userService.getUserByUsername(username);
        if (bean == null) {
            log.info("用户不存在： [ {} ]", username);
            return AjaxResult.error(ServiceExceptionEnum.USER_NOT_FOUNT_ERROR);
        }
        return AjaxResult.success(bean);
    }

    @GetMapping(value = "loadUserByPhone")
    public AjaxResult<SysUser> getUserByPhone(@RequestParam String phone) {
        if (StringUtils.isEmpty(phone)) {
            return AjaxResult.error(ServiceExceptionEnum.USER_NOT_FOUNT_ERROR);
        }
        SysUser bean = userService.getUserByPhone(phone);
        if (bean == null) {
            return AjaxResult.error(ServiceExceptionEnum.USER_NOT_FOUNT_ERROR);
        }
        return AjaxResult.success(bean);
    }

    @GetMapping(value = "getUserPerms")
    public AjaxResult<List<String>> getUserPerms(@RequestParam Long id) {
        List<String> list = userService.queryPermsByUserId(id);
        return AjaxResult.success(list);
    }
}
