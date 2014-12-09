package org.xlet.strawberry.core.message.server;

import org.xlet.strawberry.core.message.MessageType;
import org.xlet.strawberry.core.server.ServerBasic;
import org.xlet.strawberry.core.message.Message;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 上午11:43.
 * Summary: 转发消息.
 */
public class ForwardMessage extends Message implements ServerToServerMessage {

    private ServerBasic fromServer, toServer;

    private Message message;

    /**
     * 构造函数.
     */
    public ForwardMessage() {
        super(MessageType.Forward);
    }

    /**
     * 构造函数.
     *
     * @param fromServer 源服务信息.
     * @param toServer   目的服务信息.
     * @param message    消息内容.
     */
    public ForwardMessage(ServerBasic fromServer, ServerBasic toServer, Message message) {
        this();
        this.fromServer = fromServer;
        this.toServer = toServer;
        this.message = message;
    }

    /**
     * 获取目的服务信息.
     *
     * @return 目的服务信息.
     */
    public ServerBasic getToServer() {
        return toServer;
    }

    /**
     * 设置目的服务信息.
     *
     * @param toServer 目的服务信息.
     */
    public void setToServer(ServerBasic toServer) {
        this.toServer = toServer;
    }

    /**
     * 获取消息内容.
     *
     * @return 消息内容.
     */
    public Message getMessage() {
        return message;
    }

    /**
     * 设置消息内容.
     *
     * @param message 消息内容.
     */
    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public ServerBasic getFromServer() {
        return this.fromServer;
    }

    /**
     * set from server basic.
     *
     * @param fromServer from server basic.
     */
    public void setFromServer(ServerBasic fromServer) {
        this.fromServer = fromServer;
    }
}
