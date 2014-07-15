package cn.w.im.domains.messages.server;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.messages.RespondMessage;
import cn.w.im.domains.messages.ResponseMessage;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-21 下午1:56.
 * Summary: token response message
 * <p/>
 * message server response tokenResponse message that means the message server ready to connect.
 */
public class TokenResponseMessage extends ResponseMessage implements RespondMessage, ServerToServerMessage {

    private ConnectToken token;

    private ServerBasic fromServer;

    private String respondKey;

    /**
     * constructor.
     */
    public TokenResponseMessage() {
        super(MessageType.TokenResponse);
    }

    /**
     * constructor.
     *
     * @param token      token.
     * @param fromServer from server basic.
     * @param respondKey respond key.
     */
    public TokenResponseMessage(ConnectToken token, ServerBasic fromServer, String respondKey) {
        this();
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
     * @param respondKey   respond key.
     */
    public TokenResponseMessage(int errorCode, String errorMessage, ServerBasic fromServer, String respondKey) {
        super(MessageType.TokenResponse, errorCode, errorMessage);
        this.fromServer = fromServer;
        this.respondKey = respondKey;
    }

    /**
     * get token.
     *
     * @return token.
     */
    public ConnectToken getToken() {
        return token;
    }

    /**
     * set token.
     *
     * @param token token.
     */
    public void setToken(ConnectToken token) {
        this.token = token;
    }

    @Override
    public ServerBasic getFromServer() {
        return fromServer;
    }

    /**
     * set from server basic.
     *
     * @param fromServer
     */
    public void setFromServer(ServerBasic fromServer) {
        this.fromServer = fromServer;
    }

    @Override
    public String getRespondKey() {
        return respondKey;
    }

    /**
     * set respond kye.
     *
     * @param respondKey respond key.
     */
    public void setRespondKey(String respondKey) {
        this.respondKey = respondKey;
    }
}
