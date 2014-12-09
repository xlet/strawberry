package org.xlet.strawberry.core.persistent;

import org.xlet.strawberry.core.exception.ServerInnerException;
import org.xlet.strawberry.core.message.MessageType;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 上午11:01.
 * Summary: 不支持的消息类型异常.
 */
public class NotSupportMessageTypeException extends ServerInnerException {

    /**
     * 消息.
     */
    private MessageType messageType;

    /**
     * 获取消息.
     *
     * @return 消息.
     */
    public MessageType getMessageType() {
        return this.messageType;
    }

    /**
     * 构造函数.
     *
     * @param messageType message type.
     */
    public NotSupportMessageTypeException(MessageType messageType) {
        super("not support messageType[" + messageType + "]");
        this.messageType = messageType;
    }
}
