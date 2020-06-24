package cn.fllday.learn.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: gssznb
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long deptId;

    private String userName;

    private String nickName;

    private String email;

    private String phonenumber;

    private String sex;

    /** 用户头像 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 盐加密 */
    private String salt;

    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private String loginIp;

    private Date loginDate;

    private SysDept dept;

    /** 角色对象 */
    transient private List<SysRole> roles;

    /** 角色组 */
    transient private Long[] roleIds;

    /** 岗位组 */
    transient private Long[] postIds;

}
