package cn.w.im.domains.messages;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 上午11:43.
 * Summary: 转发消息.
 */
public class ForwardMessage extends Message {

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
     * 获取源服务信息.
     *
     * @return 源服务信息.
     */
    public ServerBasic getFromServer() {
        return fromServer;
    }

    /**
     * 设置源服务信息.
     *
     * @param fromServer 源服务信息.
     */
    public void setFromServer(ServerBasic fromServer) {
        this.fromServer = fromServer;
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
}
