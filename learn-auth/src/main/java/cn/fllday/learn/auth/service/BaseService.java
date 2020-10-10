package cn.fllday.learn.auth.service;

import cn.fllday.learn.exception.CustomException;
import cn.fllday.learn.pojo.user.dto.BaseDTO;
import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author gssznb
 * @Date 2020/7/16
 * @Descript:
 */

public interface BaseService {

    Logger log = LoggerFactory.getLogger(BaseService.class);

    /**
     * 设置初始页码大小
     */
    int initSize = 10;

    /**
     * 设置起始页数据大小
     */
    int initPage = 1;

    /**
     * 设置分页信息
     * @param dto 基础 dto
     */
    default void setPageSize(BaseDTO dto) {
        log.info("======================= 设置分页 ==========================");
        Integer page = dto.getPage();
        Integer pageSize = dto.getSize();
        if (page == null || page <= initPage) {
            page = initPage;
        }
        if (pageSize == null || pageSize < initSize) {
            pageSize = initSize;
        }
        log.info("[ page: {} ], [ size: {} ]", page, pageSize);
        PageHelper.startPage(page, pageSize);
    }

    default String getUsername() {
        log.info("======================= 获取用户登录信息 ================");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        throw new CustomException("用户未登录");
    }

    default String getUniqueId() {
        return IdUtil.objectId();
    }
}
