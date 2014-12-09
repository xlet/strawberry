package org.xlet.strawberry.core.message.forward;

import org.xlet.strawberry.core.message.MessageType;
import org.xlet.strawberry.core.message.Message;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午4:37.
 * Summary: forward server request connected server basic message.
 */
public class ForwardRequestMessage extends Message {
    /**
     * constructor.
     */
    public ForwardRequestMessage() {
        super(MessageType.ForwardRequest);
    }
}
