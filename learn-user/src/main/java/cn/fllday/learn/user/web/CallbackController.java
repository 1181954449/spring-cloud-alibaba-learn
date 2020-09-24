package cn.fllday.learn.user.web;

import cn.fllday.learn.common.AjaxResult;
import cn.fllday.learn.user.oauth2.provider.LearnOAuth2AuthorizationCodeAccessTokenProvider;
import cn.fllday.learn.user.oauth2.provider.LearnOAuth2ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

/**
 * @author gssznb
 * @Date 2020/7/5
 * @Descript:
 */
@RequestMapping(value = "/")
@RestController
public class CallbackController {



    private final String ACCESS_TOKEN_URI = "http://127.0.0.1:20070/oauth/token";

    private final String CLIENT_ID = "clientapp";

    private final String CLIENT_SECRET = "111111";

    private final String CLIENT_CALLBACK = "http://127.0.0.1:20050/login";
    /**
     * 授权码模式认证方式
     * @param code 用户随便访问一个接口，没有授权过的话，先引导用户到授权页面，判定是否登陆过，如果登录过 直接询问用户是否授权，如果没有登陆过先让用户登录
     *             登录成功之后 ， 引导用户授权，如果授权，则返回一个code，
     * @return
     */
    @GetMapping(value = "/login")
    public AjaxResult loginGet(@RequestParam(value = "code") String code) {
        AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
        resourceDetails.setAccessTokenUri(ACCESS_TOKEN_URI);
        resourceDetails.setClientId(CLIENT_ID);
        resourceDetails.setClientSecret(CLIENT_SECRET);

        // 创建
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
        restTemplate.getOAuth2ClientContext().getAccessTokenRequest().setAuthorizationCode(code);
        restTemplate.getOAuth2ClientContext().getAccessTokenRequest().setPreservedState(CLIENT_CALLBACK);
        restTemplate.setAccessTokenProvider(new LearnOAuth2AuthorizationCodeAccessTokenProvider());
        try {
            return AjaxResult.success(restTemplate.getAccessToken());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error();
        }
    }


    @PostMapping(value = "/login")
    public AjaxResult loginPost(@RequestParam(value = "username") String username,
                                       @RequestParam(value = "password") String password) {
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setAccessTokenUri(ACCESS_TOKEN_URI);
        resourceDetails.setClientId(CLIENT_ID);
        resourceDetails.setClientSecret(CLIENT_SECRET);
        resourceDetails.setUsername(username);
        resourceDetails.setPassword(password);
        // 创建 template
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
        restTemplate.setAccessTokenProvider(new LearnOAuth2ResourceOwnerPasswordAccessTokenProvider());
        try {
            OAuth2AccessToken accessToken = restTemplate.getAccessToken();
            return AjaxResult.success(accessToken);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
}
