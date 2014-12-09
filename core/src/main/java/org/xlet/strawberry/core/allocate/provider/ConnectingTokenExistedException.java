package org.xlet.strawberry.core.allocate.provider;

import org.xlet.strawberry.core.client.MessageClientType;
import org.xlet.strawberry.core.exception.ServerInnerException;

/**
 * when login server allocate connect token to message client,
 * login server cache this connect token to message client connected to check,
 * but add before this connect token existed,then throw this exception.
 */
public class ConnectingTokenExistedException extends ServerInnerException {

    private String token;
    private String memberId;
    private MessageClientType clientType;

    public ConnectingTokenExistedException(String token, String memberId, MessageClientType clientType) {
        super("connect token[token:" + token + ",member:" + memberId + ",clientType:" + clientType + "] existed.");
        this.token = token;
        this.memberId = memberId;
        this.clientType = clientType;
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
