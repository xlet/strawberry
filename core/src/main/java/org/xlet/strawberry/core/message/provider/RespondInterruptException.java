package org.xlet.strawberry.core.message.provider;

import org.xlet.strawberry.core.exception.ServerInnerException;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午2:23.
 * Summary: respond interrupt exception.
 * <p/>
 * when a server respond error response message,throw this.
 */
public class RespondInterruptException extends ServerInnerException {

    private String respondKey;

    /**
     * constructor.
     *
     * @param respondKey interrupt message respond key.
     */
    public RespondInterruptException(String respondKey) {
        super("the waiting interrupt.");
    }

    /**
     * get respond key.
     *
     * @return respond key.
     */
    public String getRespondKey() {
        return respondKey;
    }
}
