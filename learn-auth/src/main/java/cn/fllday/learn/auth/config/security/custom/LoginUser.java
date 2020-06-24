package cn.fllday.learn.auth.config.security.custom;

import cn.fllday.learn.pojo.user.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @Author: gssznb
 */
@Data
public class LoginUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    /** 用户唯一标识 */
    private String token;

    /** 登陆时间 */
    private Long loginTime;

    /** 过期时间 */
    private Long expireTime;

    /** 登录IP地址 */
    private String ipaddr;

    /** 登录地点 */
    private String loginLocation;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 权限列表 */
    private Set<String> permissions;

    /** 用户信息 */
    private SysUser user;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return this.user.getPassword();
    }

    public String getUsername() {
        return this.user.getUserName();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    // ...省略 set/get 方法，以及各种实现方法

}
