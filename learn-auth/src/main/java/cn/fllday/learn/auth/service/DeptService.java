package cn.fllday.learn.auth.service;

import cn.fllday.learn.pojo.user.SysDept;

import java.util.List;

/**
 * @Author: gssznb
 */
public interface DeptService extends BaseService {

    /**
     * 获取 部门集合
     * @return
     */
    List<SysDept> getSysDepts();

}
