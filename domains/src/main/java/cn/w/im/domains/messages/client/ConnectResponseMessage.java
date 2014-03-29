package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.ResponseMessage;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 上午10:04.
 * Summary: 连接应答消息.
 */
public class ConnectResponseMessage extends ResponseMessage implements ServerToClientMessage {
    /**
     * 构造函数.
     */
    public ConnectResponseMessage() {
        super(MessageType.ConnectResponse);
    }

    /**
     * 构造函数.
     *
     * @param errorCode    error code.
     * @param errorMessage error message.
     */
    public ConnectResponseMessage(int errorCode, String errorMessage) {
        super(MessageType.ConnectResponse, errorCode, errorMessage);
    }
}
