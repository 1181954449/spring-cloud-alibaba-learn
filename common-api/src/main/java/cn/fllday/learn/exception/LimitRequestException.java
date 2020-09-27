package cn.fllday.learn.exception;

/**
 * @Author: gssznb
 */
public class LimitRequestException extends CustomException {
    public LimitRequestException() {
    }

    public LimitRequestException(String message) {
        super(message);
    }

    public LimitRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimitRequestException(Throwable cause) {
        super(cause);
    }

    public LimitRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
