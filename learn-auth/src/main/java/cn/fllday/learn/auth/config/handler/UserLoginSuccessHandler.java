package cn.fllday.learn.auth.config.handler;

import cn.fllday.learn.auth.service.MenuService;
import cn.fllday.learn.auth.service.UserService;
import cn.fllday.learn.auth.utils.JwtTokenUtil;
import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.pojo.user.SysUser;
import cn.fllday.learn.pojo.user.vo.SysMenuVO;
import cn.fllday.learn.pojo.user.vo.SysUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: gssznb
 * 用户登陆成功逻辑处理
 */
@Component
@Slf4j
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication auth) throws IOException, ServletException {
        User loginUser = (User) auth.getPrincipal();
        String token = jwtTokenUtil.createJwtAccessToken(loginUser);
        final SysUser userByUsername = userService.getUserByUsername(loginUser.getUsername());
        SysUserVO vo = new SysUserVO();
        BeanUtils.copyProperties(userByUsername, vo);
        List<String> perms = menuService.findSysMenuPersByUserId(userByUsername.getUserId());
        List<SysMenuVO> menuVos = menuService.findSysMenuByUserId(userByUsername.getUserId());
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("perms", perms);
        map.put("token", token);
        map.put("userDetails", vo);
        map.put("menus", menuVos);
        AjaxResult<HashMap<String, Object>> success = AjaxResult.success(map);  
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(success.toString());
        writer.flush();
        writer.close();
    }
}
