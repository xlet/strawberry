package cn.w.im.domains.messages.server;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.MustRespondMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-17 下午3:00.
 * Summary: 请求消息服务现有已连接的客户端信息.
 */
public class RequestLinkedClientsMessage extends Message implements MustRespondMessage, ServerToServerMessage {

    private ServerBasic requestServer;

    private String respondKey;

    /**
     * 构造函数.
     */
    public RequestLinkedClientsMessage() {
        super(MessageType.RequestLinkedClients);
    }

    /**
     * 构造函数.
     *
     * @param requestServer 当前消息服务信息.
     */
    public RequestLinkedClientsMessage(ServerBasic requestServer) {
        this();
        this.requestServer = requestServer;
        this.respondKey = UUID.randomUUID().toString();
    }

    /**
     * 获取当前消息服务信息.
     *
     * @return 当前消息服务信息.
     */
    public ServerBasic getRequestServer() {
        return requestServer;
    }

    /**
     * 设置当前消息服务信息.
     *
     * @param requestServer 当前消息服务信息.
     */
    public void setRequestServer(ServerBasic requestServer) {
        this.requestServer = requestServer;
    }

    @JsonIgnore
    @Override
    public ServerBasic getFromServer() {
        return this.requestServer;
    }

    @Override
    public String getRespondKey() {
        return respondKey;
    }

    /**
     * set respond key.
     *
     * @param respondKey respond key.
     */
    public void setRespondKey(String respondKey) {
        this.respondKey = respondKey;
    }
}
