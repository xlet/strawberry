package cn.w.im.core.exception;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-21 上午10:33.
 * Summary: defined error code.
 */
public class ErrorCodeDefine {

    /**
     * login id and password error.
     */
    public final static int ID_PASSWORD_ERROR_CODE = 1001;

    /**
     * user logged in error.
     */
    public final static int LOGGED_ON_ERROR_CODE = 1002;

    /**
     * token not existed error.
     */
    public final static int TOKEN_NOT_EXISTED_ERROR_CODE = 1003;

    /**
     * token different from server cached token.
     */
    public final static int TOKEN_ERROR_CODE = 1004;

    /**
     * server inner error.
     */
    public final static int SERVER_INNER_ERROR_CODE = 9001;
}
