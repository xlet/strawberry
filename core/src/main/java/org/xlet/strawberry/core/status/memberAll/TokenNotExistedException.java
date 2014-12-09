package org.xlet.strawberry.core.status.memberAll;

import org.xlet.strawberry.core.client.MessageClientType;
import org.xlet.strawberry.core.exception.ErrorCodeDefine;
import org.xlet.strawberry.core.exception.MessageClientException;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午8:17.
 * Summary: token not existed exception.
 */
public class TokenNotExistedException extends MessageClientException {

    private String token;

    private String clientHost;

    private MessageClientType clientType;

    /**
     * constructor.
     *
     * @param clientType client type.
     * @param clientHost client host.
     * @param token      token String.
     */
    public TokenNotExistedException(MessageClientType clientType, String clientHost, String token) {
        super("clientType[" + clientType + "],clientHost[" + clientHost + "]token[" + token + "] is not existed.",
                ErrorCodeDefine.TOKEN_NOT_EXISTED_ERROR_CODE);
        this.token = token;
        this.clientHost = clientHost;
        this.clientType = clientType;
    }

    /**
     * get token String.
     *
     * @return token String.
     */
    public String getToken() {
        return token;
    }

    /**
     * get client host.
     *
     * @return client host.
     */
    public String getClientHost() {
        return clientHost;
    }

    /**
     * get client type.
     *
     * @return client type.
     */
    public MessageClientType getClientType() {
        return clientType;
    }
}
