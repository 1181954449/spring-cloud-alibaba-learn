package cn.fllday.learn.user.service;

import cn.fllday.learn.pojo.user.SysUser;

import java.util.List;

/**
 * @Author: gssznb
 */
public interface UserService {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);

    /**
     * 根据 用户 id 查询属于自己的所有权限
     * @param id 用户id
     * @return
     */
    List<String> queryPermsByUserId(Long id);

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    boolean addUser(SysUser sysUser);

}
