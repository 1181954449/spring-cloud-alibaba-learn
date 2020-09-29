package cn.fllday.learn.pojo.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: gssznb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDeptVO {

    private Long deptId;

    private String detpName;

    private Long parentId;

    private String deptPath;

    private Date established;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private Date updateBy;

}
