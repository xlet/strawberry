package org.xlet.strawberry.core.client;

import org.xlet.strawberry.core.Channel;
import org.xlet.strawberry.core.message.Message;
import org.xlet.strawberry.core.server.ServerBasic;

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
