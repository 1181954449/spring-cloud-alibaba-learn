package cn.fllday.learn.user.oauth2;

import cn.fllday.learn.common.AjaxResult;
import org.springframework.http.HttpHeaders;
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
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gssznb
 * @Date 2020/7/6
 * @Descript:
 */
public class LearnOAuth2AuthorizationCodeAccessTokenProvider extends AuthorizationCodeAccessTokenProvider {

    private ClientAuthenticationHandler authenticationHandler = new DefaultClientAuthenticationHandler();
    private RequestEnhancer tokenRequestEnhancer = new DefaultRequestEnhancer();

    @Override
    public OAuth2AccessToken retrieveToken(AccessTokenRequest request, OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form, HttpHeaders headers) throws OAuth2AccessDeniedException {
        try {
            this.authenticationHandler.authenticateTokenRequest(resource, form, headers);
            this.tokenRequestEnhancer.enhance(request, resource, form, headers);
            final ResponseExtractor<AjaxResult> delegate = this.getResponseExtractor2();
            ResponseExtractor<AjaxResult> extractor = new ResponseExtractor<AjaxResult>() {
                @Override
                public AjaxResult extractData(ClientHttpResponse response) throws IOException {
                    if (response.getHeaders().containsKey("Set-Cookie")) {
                        request.setCookie(response.getHeaders().getFirst("Set-Cookie"));
                    }

                    return (AjaxResult)delegate.extractData(response);
                }
            };
            AjaxResult execute = this.getRestTemplate().execute(this.getAccessTokenUri(resource, form), this.getHttpMethod(), this.getRequestCallback(resource, form, headers), extractor, form.toSingleValueMap());
            int status = execute.getStatus();
            if (status == 0) {
                return (OAuth2AccessToken) execute.getData();
            } else {
                throw new OAuth2AccessDeniedException("Access token denied.", resource);
            }
        } catch (OAuth2Exception var8) {
            throw new OAuth2AccessDeniedException("Access token denied.", resource, var8);
        } catch (RestClientException var9) {
            throw new OAuth2AccessDeniedException("Error requesting access token.", resource, var9);
        }
    }


    public ResponseExtractor<AjaxResult> getResponseExtractor2() {
        List<HttpMessageConverter<?>> messageConverters = (new RestTemplate()).getMessageConverters();
        return new HttpMessageConverterExtractor(AjaxResult.class, messageConverters);
    }
}
