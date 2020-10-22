package cn.fllday.learn.auth.service.impl;

import cn.fllday.learn.auth.mapper.SysUserMapper;
import cn.fllday.learn.auth.mapper.SysUserRoleMapper;
import cn.fllday.learn.auth.service.UserService;
import cn.fllday.learn.auth.sys.consts.Constants;
import cn.fllday.learn.exception.CustomException;
import cn.fllday.learn.pojo.user.SysUser;
import cn.fllday.learn.pojo.user.dto.SysUserDTO;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: gssznb
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private PasswordEncoder encoder;


    @Override
    @Cacheable(key = "'SysUser_'+args[0]", value = "SysUser")
    public SysUser getUserByUsername(String username) {
        Example example = new Example(SysUser.class);
        example.createCriteria()
                .andEqualTo("userName", username)
                .andEqualTo("delFlag", Constants.UserConstants.DEL_FLAG_YES);
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
    public Integer getUserByPhoneIsExist(String  phone) {
        Example example = new Example(SysUser.class);
        example.createCriteria()
                .andEqualTo("phonenumber", phone);
        return sysUserMapper.selectCountByExample(example);
    }

    @Override
    public Integer getUserByEmailIsExist(String email) {
        Example example = new Example(SysUser.class);
        example.createCriteria()
                .andEqualTo("email", email);
        return sysUserMapper.selectCountByExample(example);
    }

    @Override
    public Integer getUserByUsernameIsExist(String username) {
        Example example = new Example(SysUser.class);
        example.createCriteria()
                .andEqualTo("userName", username);
        return sysUserMapper.selectCountByExample(example);
    }

    @Override
    public boolean addUser(SysUserDTO dto) {
        Integer isExist = getUserByPhoneIsExist(dto.getPhonenumber());
        if (isExist != null && isExist != 0) {
            throw new CustomException("手机号码已经存在");
        }
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(dto, sysUser);
        sysUser.setDelFlag(Constants.UserConstants.DEL_FLAG_YES);
        sysUser.setStatus(Constants.UserConstants.UN_LOCKED_STATUS);
        sysUser.setCreateTime(new Date());
        sysUser.setCreateBy(getLoginUsername());
        sysUser.setPassword(encoder.encode(sysUser.getPassword()));
        int insert = sysUserMapper.insert(sysUser);
        return insert > 0;
    }


    @Override
    public PageInfo<SysUser> queryUserByPage(SysUserDTO dto) {

        setPageSize(dto);
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria()
                .andEqualTo("delFlag", Constants.UserConstants.DEL_FLAG_YES);
        if (!StringUtils.isEmpty(dto.getPhonenumber())) {
            criteria.andLike("phonenumber", "%"+dto.getPhonenumber()+"%");
        }
        if (!StringUtils.isEmpty(dto.getUserName())) {
            criteria.andLike("nickName","%"+ dto.getUserName()+"%");
        }
        if (dto.getDeptId() != null && dto.getDeptId() != 0) {
            criteria.andEqualTo("deptId", dto.getDeptId());
        }
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
