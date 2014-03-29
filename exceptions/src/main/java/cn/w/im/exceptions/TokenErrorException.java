package cn.w.im.exceptions;

import cn.w.im.domains.ErrorCodeDefine;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-26 下午8:18.
 * Summary: occurred when message client's token is different with server cached.
 */
public class TokenErrorException extends MessageClientException {
    /**
     * constructor.
     */
    public TokenErrorException() {
        super("the token is not correct.", ErrorCodeDefine.TOKENERROR);
    }
}
