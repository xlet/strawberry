package cn.w.im.domains.messages;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-17 下午3:00.
 * Summary: 请求消息服务现有已连接的客户端信息.
 */
public class RequestLinkedClientsMessage extends Message {

    private ServerBasic requestServer;

    /**
     * 构造函数.
     */
    public RequestLinkedClientsMessage() {
        super(MessageType.RequestLinkedClients);
    }

    /**
     * 构造函数.
     * @param requestServer 当前消息服务信息.
     */
    public RequestLinkedClientsMessage(ServerBasic requestServer) {
        this();
        this.requestServer = requestServer;
    }

    /**
     * 获取当前消息服务信息.
     * @return 当前消息服务信息.
     */
    public ServerBasic getRequestServer() {
        return requestServer;
    }

    /**
     * 设置当前消息服务信息.
     * @param requestServer 当前消息服务信息.
     */
    public void setRequestServer(ServerBasic requestServer) {
        this.requestServer = requestServer;
    }
}
