package cn.fllday.learn.user.web;

import cn.fllday.learn.pojo.Student;
import cn.fllday.learn.user.remote.OrderRemote;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Student getStudent(Integer id){

        return new Student(id+"", id, 1.1, 1.2, orderRemote.getOrder("TB20200624" + System.currentTimeMillis()));
    }

}
