package org.xlet.strawberry.core.message.provider;

import org.xlet.strawberry.core.exception.ServerInnerException;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 上午10:42.
 * Summary: respond provider dit not found message respond key.
 */
public class NotRegisterRespondMessageException extends ServerInnerException {

    private String respondKey;

    /**
     * constructor.
     *
     * @param respondKey message respond key.
     */
    public NotRegisterRespondMessageException(String respondKey) {
        super("the waiting for reply message flag[" + respondKey + "] dit not found.");
        this.respondKey = respondKey;
    }

    /**
     * get message respond key.
     *
     * @return message respond key.
     */
    public String getRespondKey() {
        return respondKey;
    }
}
