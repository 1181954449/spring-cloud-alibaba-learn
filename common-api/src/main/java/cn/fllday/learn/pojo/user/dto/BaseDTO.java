package cn.fllday.learn.pojo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: gssznb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDTO {

    private Integer page = 0;

    private Integer size = 15;

}
