package cn.fllday.learn.auth.service;

import cn.fllday.learn.pojo.user.dto.BaseDTO;
import com.github.pagehelper.PageHelper;

/**
 * @author gssznb
 * @Date 2020/7/16
 * @Descript:
 */
public interface BaseService {

    /**
     *
     * @param dto
     */
    default void setPageSize(BaseDTO dto) {
        Integer page = dto.getPage();
        Integer pageSize = dto.getSize();
        if (page == null && page < 0) {
            page = 0;
        }
        if (pageSize == null && pageSize < 10) {
            pageSize = 15;
        }
        PageHelper.startPage(page, pageSize);
    }

}
