package cn.fllday.learn.order.web;

import cn.fllday.learn.pojo.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: gssznb
 */
@RequestMapping(value = "/order")
@RestController
public class OrderController {

    @GetMapping(value = "get")
    public Order getOrder(@RequestParam(value = "orderName") String orderNum) {
        return new Order(orderNum, orderNum, orderNum);
    }

}
