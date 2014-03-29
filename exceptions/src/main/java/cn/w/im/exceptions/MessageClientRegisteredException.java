package cn.w.im.exceptions;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午7:18.
 * Summary: message client has registered exception.
 */
public class MessageClientRegisteredException extends ServerInnerException {

    private String loginId;

    /**
     * constructor.
     */
    public MessageClientRegisteredException(String loginId) {
        super("the message client[loginId:" + loginId + "] has registered.");
        this.loginId = loginId;
    }

    /**
     * get login id.
     *
     * @return login id.
     */
    public String getLoginId() {
        return loginId;
    }
}
