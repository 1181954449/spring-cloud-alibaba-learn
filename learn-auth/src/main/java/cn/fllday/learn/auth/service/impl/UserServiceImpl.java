package cn.fllday.learn.auth.service.impl;

import cn.fllday.learn.auth.mapper.SysUserMapper;
import cn.fllday.learn.auth.service.UserService;
import cn.fllday.learn.auth.sys.Constants;
import cn.fllday.learn.pojo.user.SysUser;
import cn.fllday.learn.pojo.user.vo.SysUserVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
}
