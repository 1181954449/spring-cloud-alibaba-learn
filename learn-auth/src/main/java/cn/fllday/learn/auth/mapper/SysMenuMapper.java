package cn.fllday.learn.auth.mapper;


import cn.fllday.learn.component.myabtis.mapper.MyMapper;
import cn.fllday.learn.pojo.user.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper extends MyMapper<SysMenu> {


    /**
     * 获取用户的菜单列表  仅限两层
     * @param userId
     * @param parentId
     * @return
     */
    List<SysMenu> selectSysMenuList(@Param("userId")Long userId, @Param("parentId") Long parentId);

    /**
     * 获取用户的权限code
     * @param userId
     * @return
     */
    List<String> selectSysMenuStrings(@Param("userId") Long userId);
}
