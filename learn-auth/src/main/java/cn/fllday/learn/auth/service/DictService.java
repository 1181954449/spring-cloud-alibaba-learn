package cn.fllday.learn.auth.service;

import cn.fllday.learn.pojo.user.SysDict;
import cn.fllday.learn.pojo.user.dto.SysDictDTO;
import cn.fllday.learn.pojo.user.dto.SysDictItemDTO;

/**
 * @Author: gssznb
 */
public interface DictService extends BaseService {

    /**
     * 添加 dict 集合
     * @param dto
     */
    void addDict(SysDictDTO dto);

    /**
     * 添加 dict item
     * @param dto
     */
    void addDictItem(SysDictItemDTO dto);

}
