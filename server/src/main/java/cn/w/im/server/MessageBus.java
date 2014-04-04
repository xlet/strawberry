package cn.w.im.server;

import cn.w.im.domains.ServerType;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午9:41.
 * Summary: 消息总线信息.
 */
public class MessageBus extends AbstractServer {

    private static MessageBus currentMessageBus;

    /**
     * 获取单例消息总线信息.
     *
     * @return 消息总线信息.
     */
    public synchronized static MessageBus current() {
        if (currentMessageBus == null) {
            currentMessageBus = new MessageBus();
        }
        return currentMessageBus;
    }

    private MessageBus() {
        super(ServerType.MessageBus);
    }
}
