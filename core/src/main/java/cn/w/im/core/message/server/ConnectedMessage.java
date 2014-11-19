package cn.w.im.core.message.server;

import cn.w.im.core.message.MessageType;
import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.message.Message;
import cn.w.im.core.message.MustRespondMessage;
import cn.w.im.core.client.MessageClientType;

import java.util.UUID;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午8:29.
 * Summary: notify message client connected.
 */
public class ConnectedMessage extends Message implements MustRespondMessage, ServerToServerMessage {

    private String token;

    private String memberId;

    private String clientHost;

    private MessageClientType clientType;

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
     * @param memberId   connected member id.
     * @param clientHost client host.
     * @param clientType client type.
     * @param fromServer from server.
     */
    public ConnectedMessage(String token, String memberId, String clientHost, MessageClientType clientType, ServerBasic fromServer) {
        this();
        this.token = token;
        this.memberId = memberId;
        this.clientHost = clientHost;
        this.clientType = clientType;
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


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getClientHost() {
        return clientHost;
    }

    public void setClientHost(String clientHost) {
        this.clientHost = clientHost;
    }

    public MessageClientType getClientType() {
        return clientType;
    }

    public void setClientType(MessageClientType clientType) {
        this.clientType = clientType;
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
