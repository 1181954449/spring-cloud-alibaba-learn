package cn.fllday.learn.auth.utils;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: gssznb
 * token 生成 类
 */
@Component
public class JwtTokenUtil {

    private static final Long EXPIR_TIME = 60L * 1000 * 60 * 2;

    private static String secret = "secret";

    private static String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    public String createJwtAccessToken(User loginUser) {
        Claims claims = new DefaultClaims();
        claims.put(LOGIN_USER_KEY, JSON.toJSONString(loginUser));
        // 登录成功生成 token
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIR_TIME))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return token;
    }

    public User getLoginUserFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        String loginUser = (String) claims.get(LOGIN_USER_KEY);
        User user = JSON.parseObject(loginUser, User.class);
        return user;
    }

    private Claims getClaimsFromToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return body;
    }

}
