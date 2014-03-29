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

    private boolean init = false;

    private MessageBus() {
        super(ServerType.MessageBus);
    }

    /**
     * 获取服务是否初始化.
     *
     * @return true:已经初始化
     */
    public boolean isInit() {
        return init;
    }

    /**
     * 初始化服务信息.
     *
     * @param host 绑定ip.
     * @param port 监听端口.
     */
    public void init(String host, int port) {
        if (!init) {
            this.setHost(host);
            this.setPort(port);
            this.init = true;
        }
    }
}
