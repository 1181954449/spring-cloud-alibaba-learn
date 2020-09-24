package cn.fllday.learn.user.oauth2.provider;

import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

/**
 * @author gssznb
 * @Date 2020/7/7
 * @Descript:
 */
public class LearnOAuth2ResourceOwnerPasswordAccessTokenProvider extends ResourceOwnerPasswordAccessTokenProvider implements CustomLearnOAuth2TokenProvider {

    @Override
    protected OAuth2AccessToken retrieveToken(AccessTokenRequest request, OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form, HttpHeaders headers) throws OAuth2AccessDeniedException {
        try {
            return this.retrieveToken2(request, resource, form, headers, this.getRestTemplate(), this.getAccessTokenUri(resource, form), this.getRequestCallback(resource, form, headers));
        } catch (OAuth2Exception var8) {
            throw new OAuth2AccessDeniedException("Access token denied.", resource, var8);
        } catch (RestClientException var9) {
            throw new OAuth2AccessDeniedException("Error requesting access token.", resource, var9);
        }
    }


}
