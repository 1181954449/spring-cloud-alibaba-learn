package cn.fllday.learn.auth.service.impl;

import cn.fllday.learn.auth.mapper.SysDictMapper;
import cn.fllday.learn.auth.service.DictService;
import cn.fllday.learn.pojo.user.SysDict;
import cn.fllday.learn.pojo.user.dto.SysDictDTO;
import cn.fllday.learn.pojo.user.dto.SysDictItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: gssznb
 */
@Service
@Slf4j
public class DictServiceImpl implements DictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public void addDict(SysDictDTO dto) {
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(dto, sysDict);
        sysDict.setId(getUniqueId());
        sysDict.setCreateBy(getUsername());
        sysDict.setCreateTime(new Date());
        sysDictMapper.insert(sysDict);
    }

    @Override
    public void addDictItem(SysDictItemDTO dto) {

    }
}
