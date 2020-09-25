package cn.fllday.learn.user.oauth2.exception;

import cn.fllday.learn.common.AjaxResult;

/**
 * @Author: gssznb
 */
public class OAuth2ResultException extends RuntimeException{

    private AjaxResult ajaxResult;

    public OAuth2ResultException(AjaxResult ajaxResult) {
        this.ajaxResult = ajaxResult;
    }

    public OAuth2ResultException(String message, AjaxResult ajaxResult) {
        super(message);
        this.ajaxResult = ajaxResult;
    }

    public OAuth2ResultException(String message, Throwable cause, AjaxResult ajaxResult) {
        super(message, cause);
        this.ajaxResult = ajaxResult;
    }

    public OAuth2ResultException(Throwable cause, AjaxResult ajaxResult) {
        super(cause);
        this.ajaxResult = ajaxResult;
    }

    public OAuth2ResultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, AjaxResult ajaxResult) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.ajaxResult = ajaxResult;
    }

    public AjaxResult getAjaxResult() {
        return ajaxResult;
    }
}
