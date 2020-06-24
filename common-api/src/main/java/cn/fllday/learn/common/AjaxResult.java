package cn.fllday.learn.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: gssznb
 */
@Data
public class AjaxResult {

    private int status;

    private String msg;

    private String exMsg;

    private Map<String,Object> data;

    private AjaxResult(ServiceExceptionEnum exceptionEnum, String exMsg) {
        this.status = exceptionEnum.getStatusCode();
        this.msg = exceptionEnum.getMsg();
        this.exMsg = exMsg;
    }

    /**
     * 成功返回
     * @return
     */
    public static AjaxResult success(){
        return new AjaxResult(ServiceExceptionEnum.SUCCESS, null);
    }

    public static AjaxResult error(){
        return new AjaxResult(ServiceExceptionEnum.SYS_ERROR, null);
    }

    public static AjaxResult error(String exMsg) {
        return new AjaxResult(ServiceExceptionEnum.SYS_ERROR, exMsg);
    }

    public static AjaxResult error(ServiceExceptionEnum errorEnum, String exMsg) {
        return new AjaxResult(errorEnum, exMsg);
    }

    public AjaxResult put(String key, Object value) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
        return this;
    }

}
