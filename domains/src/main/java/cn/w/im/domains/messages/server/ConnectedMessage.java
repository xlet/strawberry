package cn.w.im.domains.messages.server;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.MessageClientBasic;
import cn.w.im.domains.messages.Message;

import java.util.UUID;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午8:29.
 * Summary: notify message client connected.
 */
public class ConnectedMessage extends Message implements RespondMessage {

    private String token;

    private MessageClientBasic messageClientBasic;

    private String respondKey;

    private ServerBasic fromServer;

    /**
     * constructor.
     */
    public ConnectedMessage() {
        super(MessageType.Connected);
    }

    /**
     * constructor.
     *
     * @param token      connect token string.
     * @param fromServer from server.
     */
    public ConnectedMessage(String token, MessageClientBasic messageClientBasic, ServerBasic fromServer) {
        this();
        this.token = token;
        this.messageClientBasic = messageClientBasic;
        this.respondKey = UUID.randomUUID().toString();
        this.fromServer = fromServer;
    }

    /**
     * get connect token string.
     *
     * @return connect token string.
     */
    public String getToken() {
        return token;
    }

    /**
     * set connect token string.
     *
     * @param token connect token string.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * get message client basic.
     *
     * @return message client basic.
     */
    public MessageClientBasic getMessageClientBasic() {
        return messageClientBasic;
    }

    /**
     * set message client basic.
     *
     * @param messageClientBasic message client basic.
     */
    public void setMessageClientBasic(MessageClientBasic messageClientBasic) {
        this.messageClientBasic = messageClientBasic;
    }

    @Override
    public String getRespondKey() {
        return this.respondKey;
    }

    /**
     * set respond key.
     *
     * @param respondKey respond key.
     */
    public void setRespondKey(String respondKey) {
        this.respondKey = respondKey;
    }

    @Override
    public ServerBasic getFromServer() {
        return this.fromServer;
    }

    /**
     * set from server basic.
     *
     * @param fromServer from server basic.
     */
    public void setFromServer(ServerBasic fromServer) {
        this.fromServer = fromServer;
    }
}
