package cn.fllday.learn.pojo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: gssznb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRole {

    private static final long serialVersionUID = 1L;

    /**
     * 角色序号
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限
     */
    private String roleKey;

    /**
     * 角色排序
     */
    private String roleSort;

    /**
     * 数据范围
     * 1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限
     */
    private String dataScope;

    /**
     * 角色状态
     * 0=正常,1=停用
     */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 用户是否存在此角色标识 默认不存在 */
    transient private boolean flag = false;

    /** 菜单组 */
    transient private Long[] menuIds;

    /** 部门组（数据权限） */
    transient private Long[] deptIds;
}
