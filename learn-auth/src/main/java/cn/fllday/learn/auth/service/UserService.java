package cn.fllday.learn.auth.service;

import cn.fllday.learn.pojo.user.SysUser;
import cn.fllday.learn.pojo.user.dto.SysUserDTO;
import com.alibaba.fastjson.JSONObject;
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
     * 根据手机号判断是否存在
     * @param phone
     * @return
     */
    Integer getUserByPhoneIsExist(String phone);

    /**
     * 根据邮箱查看是否存在
     * @param email
     * @return
     */
    Integer getUserByEmailIsExist(String email);

    /**
     * 根据用户查询是否存在
     * @param username
     * @return
     */
    Integer getUserByUsernameIsExist(String username);

    /**
     * 添加用户
     * @param dto
     * @return
     */
    boolean addUser(SysUserDTO dto);

    /**
     * 根据 页码 和 数量 查询分页
     * @return
     */
    PageInfo<SysUser> queryUserByPage(SysUserDTO dto);

    /**
     * 根据id 删除用户
     * @param id
     */
    void deleteUserById(Long id);

    /**
     * 根据id 锁定用户
     * @param id
     */
    void lockedUserById(Long id);

}
