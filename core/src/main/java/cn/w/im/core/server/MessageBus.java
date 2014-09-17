package cn.w.im.core.server;

import cn.w.im.domains.ServerType;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午9:41.
 * Summary: 消息总线信息.
 */
public class MessageBus extends AbstractServer {

    public MessageBus(int port) {
        super(ServerType.MessageBus, port);
    }
}
