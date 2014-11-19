package cn.w.im.core.message.forward;

import cn.w.im.core.message.MessageType;
import cn.w.im.core.message.Message;

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
