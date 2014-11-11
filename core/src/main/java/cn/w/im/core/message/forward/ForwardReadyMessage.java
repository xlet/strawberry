package cn.w.im.core.message.forward;

import cn.w.im.core.MessageType;
import cn.w.im.core.message.Message;

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
