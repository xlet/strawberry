package cn.w.im.core;

import cn.w.im.core.server.AbstractServer;
import cn.w.im.core.message.Message;

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
