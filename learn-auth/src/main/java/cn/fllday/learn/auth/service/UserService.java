package cn.fllday.learn.auth.service;

import cn.fllday.learn.pojo.user.SysUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author: gssznb
 */
public interface UserService extends BaseService {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);


    /**
     * 根据手机号码查询
     * @param phone
     * @return
     */
    SysUser getUserByPhone(String phone);

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    boolean addUser(SysUser sysUser);

    /**
     * 根据 页码 和 数量 查询分页
     * @param page
     * @param size
     * @return
     */
    PageInfo<SysUser> queryUserByPage(Integer page, Integer size);

}
