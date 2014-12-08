package cn.w.im.core.allocate.provider;

import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.exception.ServerInnerException;

/**
 * login server allocate a token to message client,when message client connect message server,the
 * message server send this message client to login server,but login server can't find this token
 * info,then throw this exception.
 */
public class ConnectingTokenNotExistedException extends ServerInnerException {

    private String token;
    private String memberId;
    private MessageClientType clientType;

    public ConnectingTokenNotExistedException(String token, String memberId, MessageClientType clientType) {
        super("connect token[token:" + token + ",member:" + memberId + ",clientType:" + clientType + "] not existed.");
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getMemberId() {
        return memberId;
    }

    public MessageClientType getClientType() {
        return clientType;
    }
}
