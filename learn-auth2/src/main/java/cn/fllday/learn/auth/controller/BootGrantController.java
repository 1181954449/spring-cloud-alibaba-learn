package cn.fllday.learn.auth.controller;

import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: gssznb
 */
@Controller
@SessionAttributes("authorizationRequest")
public class BootGrantController {


    @RequestMapping(value = "/custom/confirm_access")
    public ModelAndView getAccessConfirmation(Map<String, Object> model, HttpServletRequest request) throws Exception {
        AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView view = new ModelAndView();
        view.setViewName("authorization-grant");
        view.addObject("clientId", authorizationRequest.getClientId());
        Map<String, String[]> parameterMap = request.getParameterMap();
        String[] scopes = parameterMap.get("scope");
        view.addObject("scopes", scopes);
        return view;
    }
}
