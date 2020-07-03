package cn.fllday.learn.auth.config.oauth2.grant;

import cn.fllday.learn.auth.config.details.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.Map;

/**
 * @Author: gssznb
 */
public class PhoneSmsCodeTokenGranter extends AbstractCustomTokenGranter {

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;

    public PhoneSmsCodeTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
    }

    @Override
    protected UserDetails getUserDetails(Map<String, String> paramsters) {
        return userDetailsService.loadUserByPhoneSmsCode(paramsters.get("phone"), paramsters.get("smsCode"));
    }
}
