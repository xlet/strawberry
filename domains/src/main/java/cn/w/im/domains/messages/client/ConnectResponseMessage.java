package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.basic.Member;
import cn.w.im.domains.messages.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 上午10:04.
 * Summary: 连接应答消息.
 */
public class ConnectResponseMessage extends ResponseMessage implements ServerToClientMessage {

    private Member self;

    /**
     * 构造函数.
     */
    public ConnectResponseMessage() {
        super(MessageType.ConnectResponse);
    }

    /**
     * constructor.
     *
     * @param self login member self info.
     */
    public ConnectResponseMessage(Member self) {
        this();
        this.self = self;
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


    public Member getSelf() {
        return self;
    }

    public void setSelf(Member self) {
        this.self = self;
    }
}
