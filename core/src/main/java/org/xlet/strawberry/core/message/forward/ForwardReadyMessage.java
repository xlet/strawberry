package org.xlet.strawberry.core.message.forward;

import org.xlet.strawberry.core.message.MessageType;
import org.xlet.strawberry.core.message.Message;

/**
 * creator: JackieHan.
 * datetime: 14-3-27 下午3:46.
 * summary: forward server ready.
 */
public class ForwardReadyMessage extends Message {
    /**
     * constructor.
     */
    public ForwardReadyMessage() {
        super(MessageType.ForwardReady);
    }
}
