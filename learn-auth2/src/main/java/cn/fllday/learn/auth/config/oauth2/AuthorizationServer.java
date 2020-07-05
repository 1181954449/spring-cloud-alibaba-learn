package cn.fllday.learn.auth.config.oauth2;

import cn.fllday.learn.auth.config.details.CustomUserDetailsServiceImpl;
import cn.fllday.learn.auth.config.oauth2.grant.PhonePasswordTokenGranter;
import cn.fllday.learn.auth.config.oauth2.grant.PhoneSmsCodeTokenGranter;
import cn.fllday.learn.auth.config.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: gssznb
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {


    /**
     * 通过Map 缓存ClientDetails
     * {@link MapJdbcClientDetailsServiceImpl}
     */
    @Autowired
    private MapJdbcClientDetailsServiceImpl mapJdbcClientDetailsService;
    /**
     * 密码加密方式
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 配置客户端加载方式
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(mapJdbcClientDetailsService);
    }


    /**
     * 配置获取用户详情方式
     * {@link CustomUserDetailsServiceImpl}
     */
    @Autowired
    private UserDetailsService userDetailsService;


    /**
     * 配置SpringSecurity校验用户
     * {@link SecurityConfig}
     */
    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * 配置 redis 缓存工厂
     */
    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * redisTokenStore
     * @return
     */
    @Bean
    public RedisTokenStore redisTokenStore(){
        return new RedisTokenStore(lettuceConnectionFactory);
    }

    /**
     * 配置放开哪些校验方式
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()");
    }

    /**
     * 自定义 返回认证方式
     */
    @Autowired
    private OAuth2CustomTokenEnhancer oAuth2CustomTokenEnhancer;


    private JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }


    /**
     * 配置端点访问策略。
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 添加自定义权限校验方式 以及 grant_type 类型
        List<TokenGranter> tokenGranters = getDefaultTokenGranters(endpoints.getTokenServices(),
                                                mapJdbcClientDetailsService, endpoints.getOAuth2RequestFactory());
        // 设置授权码的存储方式
        endpoints.authorizationCodeServices(jdbcAuthorizationCodeServices());
        endpoints.tokenGranter(new CompositeTokenGranter(tokenGranters));
        endpoints.authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService)
            .tokenStore(redisTokenStore())
            .tokenEnhancer(oAuth2CustomTokenEnhancer);

        endpoints.pathMapping("/oauth/confirm_access", "/custom/confirm_access");
    }

    @Autowired
    private DataSource dataSource;


    private List<TokenGranter> getDefaultTokenGranters(AuthorizationServerTokenServices tokenServices,
                                                       ClientDetailsService clientDetailsService,
                                                       OAuth2RequestFactory requestFactory) {
        List<TokenGranter> tokenGranters = new ArrayList();
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices,jdbcAuthorizationCodeServices() , clientDetailsService, requestFactory));
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory));
        ImplicitTokenGranter implicit = new ImplicitTokenGranter(tokenServices, clientDetailsService, requestFactory);
        tokenGranters.add(implicit);
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory));
        if (this.authenticationManager != null) {
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(this.authenticationManager, tokenServices, clientDetailsService, requestFactory));
        }
        return tokenGranters;
    }


    /**
     * 获取 自定义鉴权方式的 集合
     * @param tokenServices
     * @param clientDetailsService
     * @param requestFactory
     * @return
     */
    private List<TokenGranter> getTokenGranters(AuthorizationServerTokenServices tokenServices,
                                                ClientDetailsService clientDetailsService,
                                                OAuth2RequestFactory requestFactory){
        return new ArrayList<>(Arrays.asList(
                phonePasswordTokenGranter(tokenServices, clientDetailsService, requestFactory),
                phoneSmsCodeTokenGranter(tokenServices, clientDetailsService, requestFactory),
                new AuthorizationCodeTokenGranter(tokenServices, new JdbcAuthorizationCodeServices(dataSource),
                                                    clientDetailsService, requestFactory),
                new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices,
                                                        clientDetailsService, requestFactory),
                new ImplicitTokenGranter(tokenServices, clientDetailsService, requestFactory),
                new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory),
                new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory)
        ));
    }







    /**
     * 自定义 手机密码登录
     * @param tokenServices
     * @param clientDetailsService
     * @param requestFactory
     * @return
     */
    public PhonePasswordTokenGranter phonePasswordTokenGranter(AuthorizationServerTokenServices tokenServices,
                                                               ClientDetailsService clientDetailsService,
                                                               OAuth2RequestFactory requestFactory) {
        return new PhonePasswordTokenGranter(tokenServices, clientDetailsService, requestFactory, "phone_password");
    }

    /**
     * 自定义手机验证码登录的 bean
     * @param tokenServices
     * @param clientDetailsService
     * @param requestFactory
     * @return
     */
    public PhoneSmsCodeTokenGranter phoneSmsCodeTokenGranter(AuthorizationServerTokenServices tokenServices,
                                                               ClientDetailsService clientDetailsService,
                                                               OAuth2RequestFactory requestFactory) {
        return new PhoneSmsCodeTokenGranter(tokenServices, clientDetailsService, requestFactory, "phone_sms_code");
    }

}
