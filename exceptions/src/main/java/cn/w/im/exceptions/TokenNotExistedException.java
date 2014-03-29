package cn.w.im.exceptions;

import cn.w.im.domains.ErrorCodeDefine;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午8:17.
 * Summary: token not existed exception.
 */
public class TokenNotExistedException extends MessageClientException {

    private String token;

    /**
     * constructor.
     *
     * @param token token String.
     */
    public TokenNotExistedException(String token) {
        super("token[" + token + "] is not existed.", ErrorCodeDefine.TOKENNOTEXISTED);
        this.token = token;
    }

    /**
     * get token String.
     *
     * @return token String.
     */
    public String getToken() {
        return token;
    }
}
