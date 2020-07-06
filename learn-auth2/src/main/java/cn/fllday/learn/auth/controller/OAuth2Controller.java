package cn.fllday.learn.auth.controller;

import cn.fllday.learn.common.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * @Author: gssznb
 */
@Controller
@RequestMapping(value = "/oauth")
public class OAuth2Controller {

    @Autowired
    TokenEndpoint tokenEndpoint;


    @ResponseBody
    @GetMapping(value = "/token")
    public AjaxResult<OAuth2AccessToken> getAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken body = tokenEndpoint.getAccessToken(principal, parameters).getBody();
        return AjaxResult.success(body);
    }

    @ResponseBody
    @PostMapping(value = "/token")
    public AjaxResult<OAuth2AccessToken> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken body = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return AjaxResult.success(body);
    }


}
