package cn.fllday.learn.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: gssznb
 */
@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    @GetMapping(value = "/login")
    public String authLogin(){
        return "autorization-login";
    }

}
