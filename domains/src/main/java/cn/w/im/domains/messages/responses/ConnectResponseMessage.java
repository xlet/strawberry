package cn.w.im.domains.messages.responses;

import cn.w.im.domains.MessageType;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 上午10:04.
 * Summary: 连接应答消息.
 */
public class ConnectResponseMessage extends ResponseMessage {
    /**
     * 构造函数.
     *
     */
    public ConnectResponseMessage() {
        super(MessageType.ConnectResponse);
    }

    /**
     * 构造函数.
     * @param success 操作是否成功.
     */
    public ConnectResponseMessage(boolean success) {
        this();
        this.setSuccess(success);
    }
}
