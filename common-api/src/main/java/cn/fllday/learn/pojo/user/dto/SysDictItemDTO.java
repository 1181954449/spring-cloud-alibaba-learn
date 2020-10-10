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
public class SysDictItemDTO {

    private String id;

    /**
     * 字典id
     */
    private String dictId;

    /**
     * 字典项文本
     */
    private String itemText;

    /**
     * 字典项值
     */
    private String itemValue;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 状态（1启用 0不启用）
     */
    private Integer status;

}
