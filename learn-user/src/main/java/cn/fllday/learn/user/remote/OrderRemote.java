package cn.fllday.learn.user.remote;

import cn.fllday.learn.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: gssznb
 */
@FeignClient(value = "learn-order")
public interface OrderRemote {

    @GetMapping(value = "/order/get")
    Order getOrder(@RequestParam(value = "orderName") String orderNum);

}
