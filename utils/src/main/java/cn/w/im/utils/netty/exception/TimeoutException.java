package cn.w.im.utils.netty.exception;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-21 下午5:09
 * Summary:
 */
public class TimeoutException extends RuntimeException {

    public TimeoutException() {
        super();
    }

    public TimeoutException(String message) {
        super(message);
    }
}
