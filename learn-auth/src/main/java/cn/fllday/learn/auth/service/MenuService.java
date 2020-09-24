package cn.fllday.learn.auth.service;

import cn.fllday.learn.pojo.user.vo.SysMenuVO;

import java.util.List;

/**
 * @author gssznb
 * @Date 2020/7/20
 * @Descript:
 */
public interface MenuService extends BaseService {

    /**
     * 根据用户ID 获取用户的 权限菜单
     * @param userId
     * @return
     */
    List<SysMenuVO> findSysMenuByUserId(Long userId);

    /**
     * 根据用户ID 获取所有属于自己权限的标志位
     * @param userId
     * @return
     */
    List<String> findSysMenuPersByUserId(Long userId);
}
