package cn.fllday.learn.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: gssznb
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private String name;
    private int sex;
    private double height;
    private double weight;

    private Order order;

}
