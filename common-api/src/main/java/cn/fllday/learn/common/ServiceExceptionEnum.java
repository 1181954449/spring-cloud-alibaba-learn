package cn.fllday.learn.common;

import java.io.Serializable;

/**
 * @Author: gssznb
 */
public enum  ServiceExceptionEnum implements Serializable {

    // ========== 系统级别 ==========
    SUCCESS(0, "成功"),
    SYS_ERROR(2001001000, "服务器端发生异常"),
    SYS_REPEAT_REQUEST_ERROR(2001001001, "请勿重复请求"),
    SYS_LIMIT_REQUEST_ERROR(2001001002, "请求过于频繁，请稍后再试"),
    CUSTOM_ERROR(0, "自定义信息") {},

    // ==========  授权服务  ==============
    AUTH_ERROR(1001001001, "未授权"),
    AUTH_UNLOGIN_ERROR(1001001002, "未登录"),
    AUTH_USERNAME_OR_PASSWORD_ERRPR(1001001003, "用户名或密码错误"),
    AUTH_LOCKED_ERROR(1001001004, "账号被锁定"),
    AUTH_NO_CLIENT_ERROR(1001001005, "客户端ID参数不存在"),
    AUTH_MATCH_CLIENT_ERROR(1001001006, "客户端ID错误"),
    AUTH_NO_GRANT_TYPE_ERRPR(1001001007, "授权类型参数不存在"),
    AUTH_NOT_SUPPORT_IMPLICIT_ERROR(1001001008, "不支持隐式授权"),
    AUTH_UNKNOW_GRANT_TYPE_ERROR(1001001009, "未知的授权方式"),
    AUTH_CODE_ERROR(1001001010, "验证码错误"),

    // ==========  用户服务  ======================
    USER_NOT_FOUNT_ERROR(1001002001, "用户不存在"),
    USER_PARAMS_ERRPR(1001002002, "用户参数错误")


    ;

    private int statusCode;
    private String msg;

    ServiceExceptionEnum(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    private void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
