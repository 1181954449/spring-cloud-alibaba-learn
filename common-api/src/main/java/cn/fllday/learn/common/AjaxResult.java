package cn.fllday.learn.common;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: gssznb
 */
@Data
@NoArgsConstructor
public class AjaxResult<T> implements Serializable {

    private int status;

    private String msg;

    private String exMsg;

    private T data;

    private AjaxResult(ServiceExceptionEnum exceptionEnum, String exMsg, T data) {
        this.status = exceptionEnum.getStatusCode();
        this.msg = exceptionEnum.getMsg();
        this.exMsg = exMsg;
        this.data = data;
    }

    /**
     * 成功返回
     * @return
     */
    public static <T> AjaxResult<T> success(){
        return new AjaxResult<T>(ServiceExceptionEnum.SUCCESS, null, null);
    }

    public static <T> AjaxResult<T> success(T data){
        return new AjaxResult<T>(ServiceExceptionEnum.SUCCESS, null, data);
    }

    public static <T> AjaxResult<T> error(){
        return new AjaxResult<T>(ServiceExceptionEnum.SYS_ERROR, null, null);
    }

    public static <T> AjaxResult<T> error(ServiceExceptionEnum exceptionEnum) {
        return new AjaxResult<T>(exceptionEnum, "", null);
    }


    public static <T> AjaxResult<T> error(String exMsg) {
        return new AjaxResult<T>(ServiceExceptionEnum.SYS_ERROR, exMsg, null);
    }

    public static <T> AjaxResult<T> error(ServiceExceptionEnum errorEnum, String exMsg) {
        return new AjaxResult<T>(errorEnum, exMsg, null);
    }

    public static <T> AjaxResult<T> error(ServiceExceptionEnum errorEnum, T data){
        return new AjaxResult<T>(errorEnum, null, data);
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
