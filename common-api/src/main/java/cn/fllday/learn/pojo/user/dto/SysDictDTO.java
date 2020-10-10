package cn.fllday.learn.pojo.user.dto;

import cn.fllday.learn.valid.anno.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @Author: gssznb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDictDTO extends BaseDTO{

    private String id;

    /**
     * 字典名称
     */
    @NotNull(message = "字典名称不能为空")
    @Length(min = 1, max = 100, message = "字典名称长度不符合要求")
    private String dictName;

    /**
     * 字典编码
     */
    @NotNull(message = "字典名称不能为空")
    @Length(min = 1, max = 100, message = "字典名称长度不符合要求")
    private String dictCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 删除状态
     */
    @NotNull(message = "删除状态不能为空")
    @Status(status = {"0", "1"}, message = "状态值不存在", defaultVal = "1")
    private Integer delFlag;

    /**
     * 字典类型0为string,1为number
     */
    @NotNull(message = "字典类型不能为空")
    @Status(status = {"0", "1"}, message = "字典类型值不存在", defaultVal = "1")
    private Integer type;
}
