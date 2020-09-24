package cn.fllday.learn.auth.config.details;

import cn.fllday.learn.auth.service.MenuService;
import cn.fllday.learn.auth.service.UserService;
import cn.fllday.learn.pojo.user.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author: gssznb
 */
@Component
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    /**
     * 用户登录逻辑
     * @param s 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(s)) {
            log.info("用户登录： 用户名输入为空");
            throw new UsernameNotFoundException("用户名不能为空");
        }
        SysUser bean = userService.getUserByUsername(s);
        if (Objects.isNull(bean)) {
            log.info("用户登录： 用户名不存在: [ {} ]", s);
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<String> perms = menuService.findSysMenuPersByUserId(bean.getUserId());
        Collection<GrantedAuthority> authorities = new ArrayList<>(perms.size());
        perms.forEach(p -> {
            authorities.add(new SimpleGrantedAuthority(p));
        });
        return new User(bean.getUserName(), bean.getPassword(), authorities);
    }
}
