package cn.fllday.learn.auth.service;

import cn.fllday.learn.component.redis.RedisUtils;
import cn.fllday.learn.constant.Constant;
import cn.fllday.learn.exception.CaptchaExpireException;
import cn.fllday.learn.exception.UserPasswordNotMatchException;
import cn.fllday.learn.pojo.user.vo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: gssznb
 */
@Service
@Slf4j
public class SysLoginService {

    @Autowired
    TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtils redisUtils;


    public String login(String username, String password, String code, String uuid) {
        String verify = Constant.CAPTCHA_CODE_KEY + uuid;
        // 获取验证码的值
        String captcha  = (String) redisUtils.get(verify);
        // 缓存中删除验证码的值
        redisUtils.del(verify);
        if (captcha == null) {
            log.error("验证码为空: [ {} ]", code);
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            log.error("输入验证码和后台验证不一致，输入：[ {} ]， 输出： [ {} ]", code, captcha);
            throw new CaptchaExpireException();
        }
        Authentication authentication;
        try {
            // 该方法会去调用 UserDetailsServiceImpl.loadUserByUsername()
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
        } catch (RuntimeException e) {
            if (e instanceof BadCredentialsException) {
                log.error("用户名密码不正确: 用户名：[ {} ]", username);
                throw new UserPasswordNotMatchException();
            } else {
                log.error("用户登录失败: 用户名：[ {} ]", username);
                throw e;
            }
        }
        // 验证通过  记录相应的日志。
        log.info("用户:{}, 登录成功", username);
        // 生成 token
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        return tokenService.createToken(principal);

    }
}
