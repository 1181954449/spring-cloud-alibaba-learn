package cn.fllday.learn.auth.service.impl;

import cn.fllday.learn.auth.mapper.SysUserMapper;
import cn.fllday.learn.auth.mapper.SysUserRoleMapper;
import cn.fllday.learn.auth.service.UserService;
import cn.fllday.learn.auth.sys.consts.Constants;
import cn.fllday.learn.pojo.user.SysUser;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

/**
 * @Author: gssznb
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    @Cacheable(key = "'SysUser_'+args", value = "SysUser")
    public SysUser getUserByUsername(String username) {
        Example example = new Example(SysUser.class);
        example.createCriteria()
                .andEqualTo("userName", username)
                .andEqualTo("delFlag", "0");
        SysUser sysUser = sysUserMapper.selectOneByExample(example);
        return sysUser;
    }

    @Override
    public SysUser getUserByPhone(String phone) {
        Example example = new Example(SysUser.class);
        example.createCriteria()
                .andEqualTo("phonenumber", phone)
                .andEqualTo("delFlag", Constants.UserConstants.DEL_FLAG_YES);
        return sysUserMapper.selectOneByExample(example);
    }


    @Override
    public boolean addUser(SysUser sysUser) {
        int insert = sysUserMapper.insert(sysUser);
        return insert > 0;
    }


    @Override
    public PageInfo<SysUser> queryUserByPage(Integer page, Integer size) {
        setPageSize(page, size);
        Example example = new Example(SysUser.class);
        example.createCriteria()
                .andEqualTo("delFlag", Constants.UserConstants.DEL_FLAG_YES);
        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUsers);
        return pageInfo;
    }


    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void deleteUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        if (Objects.isNull(sysUser)) {
            return;
        }
        sysUser.setDelFlag(Constants.UserConstants.DEL_FLAG_NO);
        sysUserMapper.updateByPrimaryKey(sysUser);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void lockedUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        if (Objects.isNull(sysUser)) {
            return;
        }
        sysUser.setStatus(Constants.UserConstants.LOCKED_STATUS);
        sysUserMapper.updateByPrimaryKey(sysUser);
    }
}
