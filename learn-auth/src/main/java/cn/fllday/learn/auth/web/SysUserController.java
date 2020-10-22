package cn.fllday.learn.auth.web;

import cn.fllday.learn.auth.service.MenuService;
import cn.fllday.learn.auth.service.UserService;
import cn.fllday.learn.auth.sys.anno.Limit;
import cn.fllday.learn.auth.sys.anno.LocalLock;
import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.common.ServiceExceptionEnum;
import cn.fllday.learn.pojo.user.SysUser;
import cn.fllday.learn.pojo.user.dto.SysUserDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: gssznb
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
@Validated
public class SysUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

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
    public AjaxResult<List<String>> getUserPerms(@RequestParam @NotNull(message = "用户ID不能为空") Long id) {
        List<String> list = menuService.findSysMenuPersByUserId(id);
        return AjaxResult.success(list);
    }

    @GetMapping(value = "")
    public AjaxResult<PageInfo<SysUser>> getList(SysUserDTO dto) {
        PageInfo<SysUser> pageInfo = userService.queryUserByPage(dto);
        return AjaxResult.success(pageInfo);
    }

    @PostMapping(value = "")
    public AjaxResult registerUser(@Validated @RequestBody SysUserDTO dto) {
        userService.addUser(dto);
        return AjaxResult.success();
    }

    @DeleteMapping(value = "/delete")
    @LocalLock(expire = 10)
    public AjaxResult deleteUser(@NotBlank(message = "ID 不能为空") String id) {
        userService.deleteUserById(Long.parseLong(id));
        return AjaxResult.success();
    }

    @Limit(count = 100, period = 100, key = "test")
    @PostMapping(value = "/locked")
    public AjaxResult lockedUser(@NotNull(message = "用户 id 不能为空") Long id) {
        userService.lockedUserById(id);
        return AjaxResult.success();
    }

    @PostMapping(value = "/existByPhone")
    public AjaxResult userExist(@NotBlank(message = "手机号码不能为空") String phonenumber) {
        return AjaxResult.success(userService.getUserByPhoneIsExist(phonenumber));
    }

    @PostMapping(value = "/existByEmail")
    public AjaxResult userExistEmail(@NotBlank(message = "邮箱不能为空") String email){
        return AjaxResult.success(userService.getUserByEmailIsExist(email));
    }

    @PostMapping(value = "/existByUsername")
    public AjaxResult userExistUsername(@NotBlank(message = "用户名不能为空") String username) {
        return AjaxResult.success(userService.getUserByUsernameIsExist(username));
    }

    @PostMapping(value = "/uploadHeadImg")
    public AjaxResult uploadHeaderImg(@RequestParam(value = "file")MultipartFile file) {
        return AjaxResult.success();
    }

    @PostMapping(value = "/unLocked")
    public AjaxResult unLockedUser(@NotNull(message = "用户 id 不能为空")  Long id) {
        return AjaxResult.success();
    }
}
