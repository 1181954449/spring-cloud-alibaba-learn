package cn.fllday.learn.auth.web;

import cn.fllday.learn.auth.service.DeptService;
import cn.fllday.learn.common.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: gssznb
 */
@RestController
@RequestMapping(value = "dept")
public class SysDeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping(value = "/list")
    public AjaxResult getDeptList() {
        return AjaxResult.success(deptService.getSysDepts());
    }

}
