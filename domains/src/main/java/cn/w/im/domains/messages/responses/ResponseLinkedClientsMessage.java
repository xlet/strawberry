package cn.w.im.domains.messages.responses;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.MessageClientBasic;
import cn.w.im.domains.messages.Message;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-17 下午3:04.
 * Summary: 请求已连接客户端消息响应消息.
 */
public class ResponseLinkedClientsMessage extends Message {

    private ServerBasic messageServer;

    private List<MessageClientBasic> linkedClients;

    /**
     * 构造函数.
     */
    public ResponseLinkedClientsMessage() {
        super(MessageType.ResponseLinkedClients);
    }

    /**
     * 构造函数.
     *
     * @param messageServer 消息服务信息.
     * @param linkedClients 已连接的所有消息客户端.
     */
    public ResponseLinkedClientsMessage(ServerBasic messageServer, List<MessageClientBasic> linkedClients) {
        this();
        this.messageServer = messageServer;
        this.linkedClients = linkedClients;
    }

    /**
     * 获取消息服务信息.
     *
     * @return 消息服务信息.
     */
    public ServerBasic getMessageServer() {
        return messageServer;
    }

    /**
     * 设置消息服务信息.
     *
     * @param messageServer 消息服务信息.
     */
    public void setMessageServer(ServerBasic messageServer) {
        this.messageServer = messageServer;
    }

    /**
     * 获取已连接的所有消息客户端.
     *
     * @return 已连接的所有消息客户端.
     */
    public List<MessageClientBasic> getLinkedClients() {
        return linkedClients;
    }

    /**
     * 设置已连接的所有消息客户端.
     *
     * @param linkedClients 已连接的所有消息客户端.
     */
    public void setLinkedClients(List<MessageClientBasic> linkedClients) {
        this.linkedClients = linkedClients;
    }
}
