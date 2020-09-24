package cn.fllday.learn.user.oauth2;

import cn.fllday.learn.user.oauth2.handler.UserAuthAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author gssznb
 * @Date 2020/7/5
 * @Descript:  EnableGlobalMethodSecurity//启用方法注解方式来进行权限控制
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceOAuth2ServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private UserAuthAccessDeniedHandler authAccessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/user/**").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(authenticationEntryPoint);
        resources.accessDeniedHandler(authAccessDeniedHandler);
    }
}
