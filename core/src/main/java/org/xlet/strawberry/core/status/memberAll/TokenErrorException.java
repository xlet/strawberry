package org.xlet.strawberry.core.status.memberAll;

import org.xlet.strawberry.core.exception.ErrorCodeDefine;
import org.xlet.strawberry.core.exception.MessageClientException;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-26 下午8:18.
 * Summary: occurred when message client's token is different with server cached.
 */
public class TokenErrorException extends MessageClientException {

    private String token;

    private String host;

    private String connectToken;

    private String connectHost;

    /**
     * constructor.
     */
    public TokenErrorException(String token, String host, String connectToken, String connectHost) {
        super("the token is not correct.", ErrorCodeDefine.TOKEN_ERROR_CODE);
        this.token = token;
        this.host = host;
        this.connectToken = connectToken;
        this.connectHost = connectHost;
    }

    public String getInnerMessage() {
        return "the token is not correct.token:(" + token + "=" + connectToken + "),host:(" + host + "=" + connectToken + ")";
    }

    public String getToken() {
        return token;
    }

    public String getHost() {
        return host;
    }

    public String getConnectToken() {
        return connectToken;
    }

    public String getConnectHost() {
        return connectHost;
    }
}
