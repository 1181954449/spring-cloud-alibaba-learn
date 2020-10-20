package cn.fllday.learn.auth.service;

import cn.fllday.learn.pojo.user.SysDict;
import cn.fllday.learn.pojo.user.SysDictItem;
import cn.fllday.learn.pojo.user.dto.SysDictDTO;
import cn.fllday.learn.pojo.user.dto.SysDictItemDTO;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

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

    /**
     * 获取单个 SysDict
     * @param dictId
     * @return
     */
    SysDict getDictById(String dictId);

    /**
     * 字典分页
     * @param dto  条件
     * @return
     */
    PageInfo<SysDict> getDictList(SysDictDTO dto);

    /**
     * 根据 dict Id 查询 dictitem 集合
     * @param dictId
     * @return
     */
    List<JSONObject> getDictItemById(String dictId);


    /**
     * 查询对应的字典集合
     * @param dictId
     * @return
     */
    JSONObject getDictItems(String dictId);

}
