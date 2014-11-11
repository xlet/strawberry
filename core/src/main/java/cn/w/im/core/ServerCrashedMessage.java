package cn.w.im.core;

import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.message.Message;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午5:31.
 * Summary: forward server notify connected server that another connected server crashed.
 */
public class ServerCrashedMessage extends Message {

    private ServerBasic crashedServer;

    /**
     * constructor.
     */
    public ServerCrashedMessage() {
        super(MessageType.ServerCrashed);
    }

    /**
     * constructor.
     *
     * @param crashedServer crashed server basic.
     */
    public ServerCrashedMessage(ServerBasic crashedServer) {
        this();
        this.crashedServer = crashedServer;
    }

    /**
     * get crashed server basic.
     *
     * @return crashed server basic.
     */
    public ServerBasic getCrashedServer() {
        return crashedServer;
    }

    /**
     * set crashed server basic.
     *
     * @param crashedServer crashed server basic.
     */
    public void setCrashedServer(ServerBasic crashedServer) {
        this.crashedServer = crashedServer;
    }
}
