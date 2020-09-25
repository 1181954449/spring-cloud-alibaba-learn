package cn.fllday.learn.user.oauth2.provider;

import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.user.oauth2.exception.OAuth2ResultException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultRequestEnhancer;
import org.springframework.security.oauth2.client.token.RequestEnhancer;
import org.springframework.security.oauth2.client.token.auth.ClientAuthenticationHandler;
import org.springframework.security.oauth2.client.token.auth.DefaultClientAuthenticationHandler;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author gssznb
 * @Date 2020/7/8
 * @Descript:
 */
public interface CustomLearnOAuth2TokenProvider {

    ClientAuthenticationHandler authenticationHandler = new DefaultClientAuthenticationHandler();
    RequestEnhancer tokenRequestEnhancer = new DefaultRequestEnhancer();

    default OAuth2AccessToken retrieveToken2(AccessTokenRequest request,
                                            OAuth2ProtectedResourceDetails resource,
                                            MultiValueMap<String, String> form,
                                            HttpHeaders headers,
                                            RestOperations restOperations,
                                            String url,
                                            RequestCallback callback
    ) throws OAuth2AccessDeniedException {
        try {
            authenticationHandler.authenticateTokenRequest(resource, form, headers);
            tokenRequestEnhancer.enhance(request, resource, form, headers);
            final ResponseExtractor<AjaxResult> delegate = getResponseExtractor2();
            ResponseExtractor<AjaxResult> extractor = new ResponseExtractor<AjaxResult>() {
                @Override
                public AjaxResult extractData(ClientHttpResponse response) throws IOException {
                    if (response.getHeaders().containsKey("Set-Cookie")) {
                        request.setCookie(response.getHeaders().getFirst("Set-Cookie"));
                    }

                    return (AjaxResult)delegate.extractData(response);
                }
            };
            AjaxResult execute = restOperations.execute(url , getHttpMethod2(), callback, extractor, form.toSingleValueMap());
            int status = execute.getStatus();
            if (status == 0) {
                Map<String, String> tokenParams = (Map<String, String>) execute.getData();
                OAuth2AccessToken oAuth2AccessToken = DefaultOAuth2AccessToken.valueOf(tokenParams);
                return oAuth2AccessToken;
            } else {
                throw new OAuth2ResultException(execute);
            }
        } catch (OAuth2Exception var8) {
            throw new OAuth2AccessDeniedException("Access token denied.", resource, var8);
        } catch (RestClientException var9) {
            throw new OAuth2AccessDeniedException("Error requesting access token.", resource, var9);
        }
    }

    default ResponseExtractor<AjaxResult> getResponseExtractor2() {
        List<HttpMessageConverter<?>> messageConverters = (new RestTemplate()).getMessageConverters();
        return new HttpMessageConverterExtractor(AjaxResult.class, messageConverters);
    }



    default HttpMethod getHttpMethod2() {
        return HttpMethod.POST;
    }
}
