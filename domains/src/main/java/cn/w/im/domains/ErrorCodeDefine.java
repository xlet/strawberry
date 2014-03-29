package cn.w.im.domains;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-21 上午10:33.
 * Summary: defined error code.
 */
public class ErrorCodeDefine {

    /**
     * login id and password error.
     */
    public final static int IDPASSWORDERRORCODE = 1001;

    /**
     * user logged in error.
     */
    public final static int LOGGEDINERRORCODE = 1002;

    /**
     * token not existed error.
     */
    public final static int TOKENNOTEXISTED = 1003;

    /**
     * token different from server cached token.
     */
    public final static int TOKENERROR = 1004;

    /**
     * server inner error.
     */
    public final static int SERVERINNERERROR = 9001;
}
