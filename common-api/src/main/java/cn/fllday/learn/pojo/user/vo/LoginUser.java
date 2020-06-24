package cn.fllday.learn.pojo.user.vo;

import cn.fllday.learn.pojo.user.SysUser;

import java.util.Set;

/**
 * @Author: gssznb
 */
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

    // ...省略 set/get 方法，以及各种实现方法

}
