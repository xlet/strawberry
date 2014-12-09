package org.xlet.strawberry.core;

import org.xlet.strawberry.core.server.AbstractServer;
import org.xlet.strawberry.core.message.Message;

/**
 * message handler context.
 */
public class MessageHandlerContext {

    private Message message;
    private Channel channel;
    private AbstractServer server;

    public MessageHandlerContext(Message message, Channel channel, AbstractServer server) {
        this.message = message;
        this.channel = channel;
        this.server = server;
    }

    public Message getMessage() {
        return message;
    }

    public Channel getChannel() {
        return channel;
    }

    public AbstractServer getServer() {
        return server;
    }

    public String getHost() {
        return this.channel.host();
    }

    public int getPort() {
        return this.channel.port();
    }
}
