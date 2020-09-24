package cn.fllday.learn.user.oauth2.provider;

import cn.fllday.learn.common.AjaxResult;
import org.apache.logging.log4j.message.StringMapMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultRequestEnhancer;
import org.springframework.security.oauth2.client.token.OAuth2AccessTokenSupport;
import org.springframework.security.oauth2.client.token.RequestEnhancer;
import org.springframework.security.oauth2.client.token.auth.ClientAuthenticationHandler;
import org.springframework.security.oauth2.client.token.auth.DefaultClientAuthenticationHandler;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gssznb
 * @Date 2020/7/6
 * @Descript:
 */
public class LearnOAuth2AuthorizationCodeAccessTokenProvider extends AuthorizationCodeAccessTokenProvider implements CustomLearnOAuth2TokenProvider {

    @Override
    public OAuth2AccessToken retrieveToken(AccessTokenRequest request, OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form, HttpHeaders headers) throws OAuth2AccessDeniedException {
        try {
            return this.retrieveToken2(request, resource, form, headers, this.getRestTemplate(), this.getAccessTokenUri(resource, form), this.getRequestCallback(resource, form, headers));
        } catch (OAuth2Exception var8) {
            throw new OAuth2AccessDeniedException("Access token denied.", resource, var8);
        } catch (RestClientException var9) {
            throw new OAuth2AccessDeniedException("Error requesting access token.", resource, var9);
        }
    }
}
