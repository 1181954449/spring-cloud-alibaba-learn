package cn.fllday.learn.exception;

/**
 * @Author: gssznb
 */
public class RepeatRequestException extends CustomException {

    public RepeatRequestException() {
    }

    public RepeatRequestException(String message) {
        super(message);
    }

    public RepeatRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatRequestException(Throwable cause) {
        super(cause);
    }

    public RepeatRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
