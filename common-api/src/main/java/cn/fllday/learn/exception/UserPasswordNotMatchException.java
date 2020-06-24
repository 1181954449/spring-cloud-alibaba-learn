package cn.fllday.learn.exception;

/**
 * @Author: gssznb
 */
public class UserPasswordNotMatchException extends RuntimeException {

    public UserPasswordNotMatchException() {
    }

    public UserPasswordNotMatchException(String message) {
        super(message);
    }

    public UserPasswordNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserPasswordNotMatchException(Throwable cause) {
        super(cause);
    }

    public UserPasswordNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
