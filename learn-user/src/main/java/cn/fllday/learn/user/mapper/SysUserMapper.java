package cn.fllday.learn.user.mapper;


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

    /**
     * 获取 用户的权限标识
     * @param id
     * @return
     */
    List<String> queryPermsByUserId(@Param("userId") Long id);

}
