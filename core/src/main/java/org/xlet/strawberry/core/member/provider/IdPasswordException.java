package org.xlet.strawberry.core.member.provider;

import org.xlet.strawberry.core.exception.ErrorCodeDefine;
import org.xlet.strawberry.core.exception.MessageClientException;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-21 上午10:48.
 * Summary: id and password not correct error.
 */
public class IdPasswordException extends MessageClientException {


    private String id;

    /**
     * constructor.
     *
     * @param id error login id.
     */
    public IdPasswordException(String id) {
        super("id or password error.", ErrorCodeDefine.ID_PASSWORD_ERROR_CODE);
        this.id = id;
    }

    /**
     * get error login id.
     *
     * @return error login id.
     */
    public String getId() {
        return id;
    }
}
