package cn.w.im.core.client;

import cn.w.im.core.Channel;
import cn.w.im.core.message.Message;
import cn.w.im.core.server.ServerBasic;

/**
 * client default implement.
 */
public abstract class AbstractClient implements Client {

    private Channel channel;

    private ServerBasic connectedServer;

    public AbstractClient(Channel channel, ServerBasic connectedServer) {
        this.channel = channel;
        this.connectedServer = connectedServer;
    }

    @Override
    public final String host() {
        return this.channel.host();
    }

    @Override
    public final int port() {
        return this.channel.port();
    }

    @Override
    public final String key() {
        return this.host() + "@@" + this.port();
    }

    @Override
    public final void send(Message message) {
        this.channel.send(message);
    }

    @Override
    public final void close() {
        channel.close();
    }

    @Override
    public final ServerBasic connectedServer() {
        return this.connectedServer;
    }

    @Override
    public final Channel channel() {
        return this.channel;
    }
}
