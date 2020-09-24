package cn.fllday.learn.user.web;

import cn.fllday.learn.pojo.Order;
import cn.fllday.learn.pojo.Student;
import cn.fllday.learn.user.remote.OrderRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: gssznb
 */
@RequestMapping(value = "/student")
@RestController
public class StudentController {

    @Autowired
    private OrderRemote orderRemote;

    @GetMapping(value = "/get")
    @PreAuthorize("hasAuthority('sys:setting:dict:c')")
    public Student getStudent(Integer id){
        return new Student(id+"", id, 1.1, 1.2, new Order());
    }


    @GetMapping(value = "/getAccessDe")
    @PreAuthorize("hasAuthority('sys:setting:dict:c111')")
    public Student getAccessDe(Integer id){
        return new Student(id+"", id, 1.1, 1.2, new Order());
    }

}
