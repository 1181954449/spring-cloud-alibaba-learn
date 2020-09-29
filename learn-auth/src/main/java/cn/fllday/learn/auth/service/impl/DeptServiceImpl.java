package cn.fllday.learn.auth.service.impl;

import cn.fllday.learn.auth.mapper.SysDeptMapper;
import cn.fllday.learn.auth.service.DeptService;
import cn.fllday.learn.pojo.user.SysDept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: gssznb
 */
@Service
@Slf4j
public class DeptServiceImpl implements DeptService {

    @Autowired
    SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDept> getSysDepts() {
        return sysDeptMapper.selectAll();
    }
}
