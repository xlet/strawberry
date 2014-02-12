package cn.w.im.domains.messages.responses;

import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.RegisterMessageType;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-13 下午5:21.
 * Summary: 消息服务注册返回消息.
 */
public class MessageServerRegisterResponseMessage extends RegisterResponseMessage {

    private List<ServerBasic> startedServers;

    /**
     * 获取已经启动的消息服务列表.
     * @return 消息服务列表。
     */
    public List<ServerBasic> getStartedServers() {
        return startedServers;
    }

    /**
     * 设置已经启动的消息服务列表.
     * @param startedServers 消息服务列表.
     */
    public void setStartedServers(List<ServerBasic> startedServers) {
        this.startedServers = startedServers;
    }

    /**
     * 默认构造函数.
     */
    public MessageServerRegisterResponseMessage() {
        super();
        this.setMessageType(MessageType.RegisterResponse);
    }

    /**
     * 构造函数.
     * @param success 注册是否成功.
     */
    public MessageServerRegisterResponseMessage(boolean success) {
        super(success, RegisterMessageType.MessageServerRegister);
    }

    /**
     * 构造函数.
     * @param success 注册是否成功.
     * @param startedServers 已经启动的消息服务列表.
     */
    public MessageServerRegisterResponseMessage(boolean success, List<ServerBasic> startedServers) {
        super(success, RegisterMessageType.MessageServerRegister);
        this.startedServers = startedServers;
    }
}
