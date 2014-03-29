package cn.w.im.domains.messages.forward;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.Message;

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
