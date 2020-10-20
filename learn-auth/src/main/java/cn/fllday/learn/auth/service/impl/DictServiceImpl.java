package cn.fllday.learn.auth.service.impl;

import cn.fllday.learn.auth.mapper.SysDictItemMapper;
import cn.fllday.learn.auth.mapper.SysDictMapper;
import cn.fllday.learn.auth.service.DictService;
import cn.fllday.learn.pojo.user.SysDict;
import cn.fllday.learn.pojo.user.SysDictItem;
import cn.fllday.learn.pojo.user.dto.SysDictDTO;
import cn.fllday.learn.pojo.user.dto.SysDictItemDTO;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: gssznb
 */
@Service
@Slf4j
public class DictServiceImpl implements DictService {

    @Autowired
    private SysDictMapper sysDictMapper;
    @Autowired
    private SysDictItemMapper sysDictItemMapper;

    @Override
    public void addDict(SysDictDTO dto) {
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(dto, sysDict);
        sysDict.setId(getUniqueId());
        sysDict.setCreateBy(getLoginUsername());
        sysDict.setCreateTime(getNow());
        sysDictMapper.insert(sysDict);
    }

    @Override
    public void addDictItem(SysDictItemDTO dto) {
        SysDictItem sysDictItem = new SysDictItem();
        BeanUtils.copyProperties(dto, sysDictItem);
        sysDictItem.setId(getUniqueId());
        sysDictItem.setCreateBy(getLoginUsername());
        sysDictItem.setCreateTime(getNow());
        sysDictItemMapper.insert(sysDictItem);
    }

    @Override
    public SysDict getDictById(String dictId) {
        SysDict sysDict = sysDictMapper.selectByPrimaryKey(dictId);
        return sysDict;
    }

    @Override
    public PageInfo<SysDict> getDictList(SysDictDTO dto) {
        setPageSize(dto);
        Example example = new Example(SysDict.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(dto.getDictName())) {
            criteria.andEqualTo("dictName", dto.getDictName());
        }
        if (!StringUtils.isEmpty(dto.getDictCode())) {
            criteria.andEqualTo("dictCode", dto.getDictCode());
        }
        List<SysDict> sysDicts = sysDictMapper.selectByExample(example);
        PageInfo<SysDict> sysDictPageInfo = new PageInfo<>(sysDicts);
        return sysDictPageInfo;
    }

    @Override
    public List<JSONObject> getDictItemById(String dictId) {
        List<SysDictItem> sysDictItems = getDictItemsList(dictId);
        return sysDictItems.stream().map(item -> {
            String itemValue = item.getItemValue();
            String itemText = item.getItemText();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(itemValue, itemText);
            return jsonObject;
        }).collect(Collectors.toList());
    }

    @Override
    public JSONObject getDictItems(String dictId) {
        List<SysDictItem> items = getDictItemsList(dictId);
        SysDict sysDict = getDictById(dictId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("dict", sysDict);
        resultJson.put("items", items);
        return resultJson;
    }

    /**
     * 封装获取 dictItems 集合
     * @param dictId
     * @return
     */
    private List<SysDictItem> getDictItemsList(String dictId) {
        Example example = new Example(SysDictItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dictId", dictId);
        List<SysDictItem> sysDictItems = sysDictItemMapper.selectByExample(example);
        return sysDictItems;
    }
}
