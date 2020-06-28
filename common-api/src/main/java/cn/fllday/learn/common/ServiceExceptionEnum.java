package cn.fllday.learn.common;

import java.io.Serializable;

/**
 * @Author: gssznb
 */
public enum  ServiceExceptionEnum implements Serializable {

    // ========== 系统级别 ==========
    SUCCESS(0, "成功"),
    SYS_ERROR(2001001000, "服务器端发生异常"),
    CUSTOM_ERROR(0, "自定义信息") {

    },

    // ==========  授权服务  ==============
    AUTH_ERROR(1001001001, "未授权"),
    AUTH_UNLOGIN_ERROR(1001001002, "未登录"),
    AUTH_USERNAME_OR_PASSWORD_ERRPR(1001001003, "用户名或密码错误"),
    AUTH_LOCKED_ERROR(1001001004, "账号被锁定"),


    // ==========  用户服务  ======================
    USER_NOT_FOUNT_ERROR(1001002001, "用户不存在")


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
