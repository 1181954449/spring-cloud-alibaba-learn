package cn.fllday.learn.auth.config.security.token;

import cn.fllday.learn.auth.config.security.custom.LoginUser;
import cn.fllday.learn.constant.Constant;

import java.util.Map;
import java.util.UUID;

/**
 * @Author: gssznb
 */
public class TokenService {

    private int expireTime = 60 * 1000 * 60 * 60 * 2;

    public String createToken(LoginUser loginUser) {
        // <1> 设置 LoginUser 的用户唯一标识。注意，这里虽然变量名叫 token ，其实不是身份认证的 Token
        String token = UUID.randomUUID().toString().replace("-", "");
        loginUser.setToken(token);
        // <2> 设置用户终端相关的信息，包括 IP、城市、浏览器、操作系统
        setUserAgent(loginUser);

        // <3> 记录缓存
        refreshToken(loginUser);

        // <4> 生成 JWT 的 Token
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    public void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }


    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据 uuid 将 loginUser 缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }
}
