package cn.fllday.learn.exception;

/**
 * @Author: gssznb
 */
public class CaptchaExpireException extends RuntimeException {

    public CaptchaExpireException() {
    }

    public CaptchaExpireException(String message) {
        super(message);
    }

    public CaptchaExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaExpireException(Throwable cause) {
        super(cause);
    }

    public CaptchaExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
