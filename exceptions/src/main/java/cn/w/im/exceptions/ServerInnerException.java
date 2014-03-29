package cn.w.im.exceptions;

import cn.w.im.domains.ErrorCodeDefine;

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
        this.errorCode = ErrorCodeDefine.SERVERINNERERROR;
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
