package cn.fllday.learn.pojo.user;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "sys_dept")
public class SysDept {
    @Id
    @Column(name = "dept_id")
    private Long deptId;

    @Column(name = "detp_name")
    private String detpName;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "dept_path")
    private String deptPath;

    private Date established;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "update_by")
    private Date updateBy;

    /**
     * @return dept_id
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * @param deptId
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * @return detp_name
     */
    public String getDetpName() {
        return detpName;
    }

    /**
     * @param detpName
     */
    public void setDetpName(String detpName) {
        this.detpName = detpName;
    }

    /**
     * @return parent_id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @return dept_path
     */
    public String getDeptPath() {
        return deptPath;
    }

    /**
     * @param deptPath
     */
    public void setDeptPath(String deptPath) {
        this.deptPath = deptPath;
    }

    /**
     * @return established
     */
    public Date getEstablished() {
        return established;
    }

    /**
     * @param established
     */
    public void setEstablished(Date established) {
        this.established = established;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return create_by
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * @return update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return update_by
     */
    public Date getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     */
    public void setUpdateBy(Date updateBy) {
        this.updateBy = updateBy;
    }
}