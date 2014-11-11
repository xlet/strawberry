package cn.w.im.core.exception;

import cn.w.im.core.MessageClientType;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午7:18.
 * Summary: message client has registered exception.
 */
public class MessageClientRegisteredException extends ServerInnerException {

    private String memberId;

    private MessageClientType messageClientType;

    /**
     * constructor.
     *
     * @param messageClientType client type.
     * @param memberId          member id.
     */
    public MessageClientRegisteredException(MessageClientType messageClientType, String memberId) {
        super("the message client[clientType:" + messageClientType + ",memberId:" + memberId + "] has registered.");
        this.memberId = memberId;
        this.messageClientType = messageClientType;
    }

    /**
     * get login id.
     *
     * @return login id.
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * get client type.
     *
     * @return client type.
     */
    public MessageClientType getMessageClientType() {
        return this.messageClientType;
    }
}
