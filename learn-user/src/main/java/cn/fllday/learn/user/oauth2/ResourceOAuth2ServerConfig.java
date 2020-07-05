package cn.fllday.learn.user.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author gssznb
 * @Date 2020/7/5
 * @Descript:
 */
@Configuration
@EnableResourceServer
public class ResourceOAuth2ServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/user/**").permitAll()
                .anyRequest().authenticated();
    }
}
