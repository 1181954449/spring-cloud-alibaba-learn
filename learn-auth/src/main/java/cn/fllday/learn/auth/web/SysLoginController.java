package cn.fllday.learn.auth.web;

import cn.fllday.learn.common.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: gssznb
 */
@RestController
public class SysLoginController {


    @PostMapping(value = "/login")
    public AjaxResult login(String username, String passwrod, String code, String uuid){
        AjaxResult success = AjaxResult.success();
        return success;
    }

}
