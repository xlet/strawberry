package cn.w.im.core.message.server;

import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.message.MessageType;
import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.message.RespondMessage;
import cn.w.im.core.message.ResponseMessage;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午8:47.
 * Summary: send this message when server received ConnectedMessage.
 */
public class ConnectedResponseMessage extends ResponseMessage implements RespondMessage, ServerToServerMessage {

    private String memberId;

    private String clientHost;

    private MessageClientType clientType;

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
     * @param memberId   member id.
     * @param clientHost client host.
     * @param clientType client type.
     * @param token      token string.
     * @param fromServer from server basic.
     * @param respondKey must responded message key.
     */
    public ConnectedResponseMessage(String memberId, String clientHost, MessageClientType clientType,
                                    String token, ServerBasic fromServer, String respondKey) {
        this();
        this.memberId = memberId;
        this.clientHost = clientHost;
        this.clientType = clientType;
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
