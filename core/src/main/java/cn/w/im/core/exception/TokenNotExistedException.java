package cn.w.im.core.exception;

import cn.w.im.core.ErrorCodeDefine;
import cn.w.im.core.MessageClientType;

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
                ErrorCodeDefine.TOKENNOTEXISTED);
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
