package cn.fllday.learn.auth.config.details;

import cn.fllday.learn.auth.remote.UserRemote;
import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.common.ServiceExceptionEnum;
import cn.fllday.learn.component.redis.RedisUtils;
import cn.fllday.learn.pojo.user.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author: gssznb
 */
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRemote userRemote;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 用户登录逻辑
     * 用户名密码登录逻辑
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

        AjaxResult<SysUser> beanResult = userRemote.getUserByUsername(s);
        if (beanResult.getStatus() == ServiceExceptionEnum.USER_NOT_FOUNT_ERROR.getStatusCode()) {
            log.info("用户登录： 用户名不存在: [ {} ]", s);
            throw new UsernameNotFoundException("用户名不存在");
        }
        return getUserDetails(beanResult);
    }

    public UserDetails loadUserByPhonePassword(String phone, String password) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            log.info("用户登录： 手机号码为空 或 密码为空");
            throw new InvalidGrantException("用户名和密码不能为空");
        }
        AjaxResult<SysUser> beanResult = userRemote.getUserByPhone(phone);
        if (beanResult.getStatus() == ServiceExceptionEnum.USER_NOT_FOUNT_ERROR.getStatusCode()) {
            log.info("用户登录： 用户不存在: [ {} ]", phone);
            throw new InvalidGrantException("用户名不存在");
        }
        return getUserDetails(beanResult);
    }

    public UserDetails loadUserByPhoneSmsCode(String phone, String smsCode) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(smsCode)) {
            log.error("用户登录： 手机号码为空 或 验证码为空");
            throw new InvalidGrantException("用户名和密码不能为空");
        }
        String redisCode = (String) redisUtils.get(phone);
        if (redisCode == null) {
            log.error("用户登录：[ {} ] 验证码已过期: [ {} ]", phone, smsCode);
            throw new InvalidGrantException("验证码已过期，请重试");
        }
        redisUtils.del(phone);
        if (!redisCode.equalsIgnoreCase(smsCode)) {
            log.error("用户登录：[ {} ] 验证码不正确: [ {} ]", phone, smsCode);
            throw new InvalidGrantException("验证码已过期，请重试");
        }
        AjaxResult<SysUser> beanResult = userRemote.getUserByPhone(phone);
        if (beanResult.getStatus() == ServiceExceptionEnum.USER_NOT_FOUNT_ERROR.getStatusCode()) {
            log.info("用户登录： 用户不存在: [ {} ]", phone);
            throw new InvalidGrantException("用户名不存在");
        }
        return getUserDetails(beanResult);
    }

    private UserDetails getUserDetails(AjaxResult<SysUser> beanResult) {
        SysUser bean = beanResult.getData();
        AjaxResult<List<String>> permsResult = userRemote.getUserPerms(bean.getUserId());
        List<String> perms = permsResult.getData();
        Collection<GrantedAuthority> authorities = new ArrayList<>(perms.size());
        perms.forEach(p -> {
            authorities.add(new SimpleGrantedAuthority(p));
        });
        return new User(bean.getUserName(), bean.getPassword(), authorities);
    }

}
