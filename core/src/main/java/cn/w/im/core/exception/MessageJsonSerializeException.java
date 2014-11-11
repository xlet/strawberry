package cn.w.im.core.exception;

import cn.w.im.core.message.Message;

/**
 * message serialize error.
 */
public class MessageJsonSerializeException extends ServerInnerException {
    private Message message;

    public MessageJsonSerializeException(Message message, Throwable e) {
        super("message[" + message.getMessageType() + "] json serialize error.", e);
        this.message = message;
    }

    public Message getImMessage() {
        return message;
    }
}
