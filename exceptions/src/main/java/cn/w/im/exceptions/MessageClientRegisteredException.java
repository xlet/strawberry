package cn.w.im.exceptions;

import cn.w.im.domains.client.MessageClientType;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午7:18.
 * Summary: message client has registered exception.
 */
public class MessageClientRegisteredException extends ServerInnerException {

    private String loginId;

    private MessageClientType messageClientType;

    /**
     * constructor.
     */
    public MessageClientRegisteredException(MessageClientType messageClientType, String loginId) {
        super("the message client[clientType:" + messageClientType + ",loginId:" + loginId + "] has registered.");
        this.loginId = loginId;
        this.messageClientType = messageClientType;
    }

    /**
     * get login id.
     *
     * @return login id.
     */
    public String getLoginId() {
        return loginId;
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
