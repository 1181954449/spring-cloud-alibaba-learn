package cn.fllday.learn.auth.mapper;


import cn.fllday.learn.component.myabtis.mapper.MyMapper;
import cn.fllday.learn.pojo.user.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: gssznb
 */
@Mapper
public interface SysUserMapper extends MyMapper<SysUser> {
}
