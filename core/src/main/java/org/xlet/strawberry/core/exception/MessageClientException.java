package org.xlet.strawberry.core.exception;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午1:59.
 * Summary: message client exception.
 */
public abstract class MessageClientException extends Exception {

    private int errorCode;

    /**
     * constructor.
     *
     * @param errorMessage error message.
     * @param errorCode    error code.
     */
    public MessageClientException(String errorMessage, int errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
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
