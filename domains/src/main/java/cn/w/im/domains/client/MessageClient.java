package cn.w.im.domains.client;

import io.netty.channel.ChannelHandlerContext;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-17 下午3:59.
 * Summary: message client.
 */
public class MessageClient extends Client {

    private String loginId;

    private MessageClientType messageClientType;

    /**
     * get login id.
     *
     * @return login id.
     */
    public String getLoginId() {
        return this.loginId;
    }

    /**
     * get client type.
     *
     * @return client type.
     */
    public MessageClientType getMessageClientType() {
        return this.messageClientType;
    }

    /**
     * constructor.
     *
     * @param messageClientType client type.
     * @param loginId    login id.
     */
    public MessageClient(ChannelHandlerContext context, MessageClientType messageClientType, String loginId) {
        super(context);
        this.messageClientType = messageClientType;
        this.loginId = loginId;
    }

    @Override
    public String toString() {
        return "MessageClient{" +
                "loginId='" + loginId + '\'' +
                ", messageClientType=" + messageClientType +
                ", key="+super.toString()+
                '}';
    }
}
