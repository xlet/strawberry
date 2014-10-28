package cn.w.im.exceptions;

import cn.w.im.domains.ErrorCodeDefine;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-26 下午8:18.
 * Summary: occurred when message client's token is different with server cached.
 */
public class TokenErrorException extends MessageClientException {

    private String tokenId;

    private String host;

    private String loginId;

    private String loginHost;

    /**
     * constructor.
     */
    public TokenErrorException(String tokenId, String host, String loginId, String loginHost) {
        super("the token is not correct.", ErrorCodeDefine.TOKENERROR);
        this.tokenId = tokenId;
        this.host = host;
        this.loginId = loginId;
        this.loginHost = loginHost;
    }

    public String getInnerMessage() {
        return "the token is not correct.loginId:(" + tokenId + "=" + loginId + "),host:(" + host + "=" + loginHost + ")";
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getHost() {
        return host;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getLoginHost() {
        return loginHost;
    }
}
