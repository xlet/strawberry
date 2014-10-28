package cn.w.im.domains.messages.server;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.MessageClientBasic;
import cn.w.im.domains.messages.RespondMessage;
import cn.w.im.domains.messages.ResponseMessage;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午8:47.
 * Summary: send this message when server received ConnectedMessage.
 */
public class ConnectedResponseMessage extends ResponseMessage implements RespondMessage, ServerToServerMessage {

    private MessageClientBasic connectedClient;

    private String token;

    private ServerBasic fromServer;

    private String respondKey;

    /**
     * constructor.
     */
    public ConnectedResponseMessage() {
        super(MessageType.ConnectedResponse);
    }

    /**
     * constructor.
     *
     * @param connectedClient connected client.
     * @param token           token string.
     * @param fromServer      from server basic.
     * @param respondKey      must responded message key.
     */
    public ConnectedResponseMessage(MessageClientBasic connectedClient, String token, ServerBasic fromServer, String respondKey) {
        this();
        this.connectedClient = connectedClient;
        this.token = token;
        this.fromServer = fromServer;
        this.respondKey = respondKey;
    }

    /**
     * constructor.
     *
     * @param errorCode    error code.
     * @param errorMessage error message.
     * @param fromServer   from server basic.
     * @param respondKey   must responded message key.
     */
    public ConnectedResponseMessage(int errorCode, String errorMessage, ServerBasic fromServer, String respondKey) {
        super(MessageType.ConnectedResponse, errorCode, errorMessage);
        this.fromServer = fromServer;
        this.respondKey = respondKey;
    }

    /**
     * get connected client.
     *
     * @return connected client.
     */
    public MessageClientBasic getConnectedClient() {
        return connectedClient;
    }

    /**
     * set connected client.
     *
     * @param connectedClient connected client.
     */
    public void setConnectedClient(MessageClientBasic connectedClient) {
        this.connectedClient = connectedClient;
    }

    /**
     * get connect token.
     *
     * @return connect token.
     */
    public String getToken() {
        return token;
    }

    /**
     * set connect token.
     *
     * @param token connect token.
     */
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public ServerBasic getFromServer() {
        return fromServer;
    }

    /**
     * set from server basic.
     *
     * @param fromServer from server basic.
     */
    public void setFromServer(ServerBasic fromServer) {
        this.fromServer = fromServer;
    }

    @Override
    public String getRespondKey() {
        return respondKey;
    }

    /**
     * set must responded message key.
     *
     * @param respondKey must responded message key.
     */
    public void setRespondKey(String respondKey) {
        this.respondKey = respondKey;
    }
}
