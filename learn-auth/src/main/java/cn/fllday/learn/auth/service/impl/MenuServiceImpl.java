package cn.fllday.learn.auth.service.impl;

import cn.fllday.learn.auth.mapper.SysMenuMapper;
import cn.fllday.learn.auth.service.MenuService;
import cn.fllday.learn.pojo.user.SysMenu;
import cn.fllday.learn.pojo.user.vo.SysMenuVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gssznb
 * @Date 2020/7/20
 * @Descript:
 */
@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;


    @Override
    @Cacheable(value = "SysUser", key = "'menu_dto_'+args")
    public List<SysMenuVO> findSysMenuByUserId(Long userId) {
        List<SysMenu> sysMenus = sysMenuMapper.selectSysMenuList(userId, 0L);
        List<SysMenuVO> collect = sysMenus.stream()
                .map(sysMenu -> {
                    SysMenuVO sysMenuVO = new SysMenuVO();
                    BeanUtils.copyProperties(sysMenu, sysMenuVO);
                    return sysMenuVO;
                })
                .collect(Collectors.toList());
        collect.forEach(sysMenuVO -> {
            List<SysMenu> subMenus = sysMenuMapper.selectSysMenuList(userId, sysMenuVO.getMenuId());
            List<SysMenuVO> subMenuVos = subMenus.stream()
                    .map(subMenu -> {
                        SysMenuVO subMenuVo = new SysMenuVO();
                        BeanUtils.copyProperties(subMenu, subMenuVo);
                        return subMenuVo;
                    })
                    .collect(Collectors.toList());
            sysMenuVO.setChildren(subMenuVos);
        });
        return collect;
    }


    @Override
    @Cacheable(key = "'menus_'+args", value = "SysUser")
    public List<String> findSysMenuPersByUserId(Long userId) {
        List<String> menusStrs = sysMenuMapper.selectSysMenuStrings(userId);
        return menusStrs;
    }
}
