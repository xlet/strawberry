package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.Message;

/**
 * author jackie.
 * message for web service connect message server.
 */
public class WebServerConnectMessage extends Message implements ClientToServerMessage {
    private String token;

    private MessageClientType messageClientType;

    /**
     * constructor.
     *
     * @param token special token.
     */
    public WebServerConnectMessage(String token) {
        super(MessageType.WebServerConnect);
        this.token = token;
        this.messageClientType = MessageClientType.Web;
    }

    /**
     * get token.
     *
     * @return token.
     */
    public String getToken() {
        return token;
    }

    /**
     * set token.
     *
     * @param token token.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * get message client type.
     *
     * @return message client type.see{@link cn.w.im.domains.client.MessageClientType}.
     */
    public MessageClientType getMessageClientType() {
        return messageClientType;
    }

    /**
     * set message client type.
     *
     * @param messageClientType message client type.see{@link cn.w.im.domains.client.MessageClientType}.
     */
    public void setMessageClientType(MessageClientType messageClientType) {
        this.messageClientType = messageClientType;
    }
}
