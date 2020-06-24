package cn.fllday.learn.auth.config.security.token;

import cn.fllday.learn.pojo.user.vo.LoginUser;

/**
 * @Author: gssznb
 */
public class TokenService {

    public String createToken(LoginUser loginUser) {
        // <1> 设置 LoginUser 的用户唯一标识。注意，这里虽然变量名叫 token ，其实不是身份认证的 Token
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        // <2> 设置用户终端相关的信息，包括 IP、城市、浏览器、操作系统
        setUserAgent(loginUser);

        // <3> 记录缓存
        refreshToken(loginUser);

        // <4> 生成 JWT 的 Token
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

}
