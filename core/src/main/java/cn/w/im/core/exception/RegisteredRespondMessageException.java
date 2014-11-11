package cn.w.im.core.exception;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 上午10:47.
 * Summary: the waiting for reply message has registered.
 */
public class RegisteredRespondMessageException extends ServerInnerException {

    private String respondKey;

    /**
     * constructor.
     *
     * @param respondKey message respond key.
     */
    public RegisteredRespondMessageException(String respondKey) {
        super("the waiting for reply message flag[" + respondKey + "] has registered.");
        this.respondKey = respondKey;
    }

    /**
     * get message respond key.
     *
     * @return message respond key.
     */
    public String getRespondKey() {
        return respondKey;
    }
}
