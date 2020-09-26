package cn.fllday.learn.auth.web;

import cn.fllday.learn.auth.service.MenuService;
import cn.fllday.learn.auth.service.UserService;
import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.common.ServiceExceptionEnum;
import cn.fllday.learn.pojo.user.SysUser;
import cn.fllday.learn.pojo.user.dto.SysUserDTO;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: gssznb
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
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
    public AjaxResult<PageInfo<SysUser>> getList(@RequestParam(required = true, defaultValue = "0") Integer page,
                                             @RequestParam(required = false, defaultValue = "15") Integer size) {
        PageInfo<SysUser> pageInfo = userService.queryUserByPage(page, size);
        return AjaxResult.success(pageInfo);
    }

    @PostMapping(value = "")
    public AjaxResult registerUser(@Validated @RequestBody SysUserDTO dto) {
        return AjaxResult.success();
    }

    @PostMapping(value = "/delete")
    public AjaxResult deleteUser(@NotBlank(message = "name 不能为空") @Length(min = 2, max = 10, message = "name 长度必须在 {min} - {max} 之间") String id,
                                 @NotBlank(message = "id2 不能为空")  String id2) {
        userService.deleteUserById(Long.parseLong(id));
        return AjaxResult.success();
    }

    @PostMapping(value = "/locked")
    public AjaxResult lockedUser(@NotNull(message = "用户 id 不能为空") Long id) {
        userService.lockedUserById(id);
        return AjaxResult.success();
    }

    @PostMapping(value = "/unLocked")
    public AjaxResult unLockedUser(@NotNull(message = "用户 id 不能为空")  Long id) {
        return AjaxResult.success();
    }
}
