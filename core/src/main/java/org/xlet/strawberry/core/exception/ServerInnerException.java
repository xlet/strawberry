package org.xlet.strawberry.core.exception;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午1:49.
 * Summary: server inner exception.
 */
public abstract class ServerInnerException extends Exception {

    private int errorCode;

    /**
     * constructor.
     *
     * @param errorMessage error message.
     */
    public ServerInnerException(String errorMessage) {
        super(errorMessage);
        this.errorCode = ErrorCodeDefine.SERVER_INNER_ERROR_CODE;
    }

    /**
     * constructor.
     *
     * @param errorMessage error message.
     * @param throwable    inner exception.
     */
    public ServerInnerException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
        this.errorCode = ErrorCodeDefine.SERVER_INNER_ERROR_CODE;
    }

    /**
     * get error code.
     *
     * @return error code.
     */
    public int getErrorCode() {
        return errorCode;
    }
}
