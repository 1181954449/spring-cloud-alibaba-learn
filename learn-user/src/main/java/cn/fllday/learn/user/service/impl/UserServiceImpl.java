package cn.fllday.learn.user.service.impl;

import cn.fllday.learn.pojo.user.SysUser;
import cn.fllday.learn.user.mapper.SysUserMapper;
import cn.fllday.learn.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: gssznb
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
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
                .andEqualTo("delFlag", "0");
        return sysUserMapper.selectOneByExample(example);
    }

    @Override
    public List<String> queryPermsByUserId(Long id) {
        return sysUserMapper.queryPermsByUserId(id);
    }


    @Override
    public boolean addUser(SysUser sysUser) {
        int insert = sysUserMapper.insert(sysUser);
        return insert > 0;
    }
}
