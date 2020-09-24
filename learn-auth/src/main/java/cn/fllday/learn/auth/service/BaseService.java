package cn.fllday.learn.auth.service;

import com.github.pagehelper.PageHelper;

/**
 * @author gssznb
 * @Date 2020/7/16
 * @Descript:
 */
public interface BaseService {

    default void setPageSize(Integer page, Integer pageSize) {
        if (page == null && page < 0) {
            page = 0;
        }
        if (pageSize == null && pageSize < 10) {
            pageSize = 15;
        }
        PageHelper.startPage(page, pageSize);
    }

}
