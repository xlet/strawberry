package cn.w.im.core.providers.client;

import cn.w.im.core.Channel;
import cn.w.im.core.message.Message;

/**
 * client default implement.
 */
public abstract class AbstractClient implements Client {

    private String host;
    private int port;

    private Channel channel;

    public AbstractClient(Channel channel) {
        this.host = channel.currentHost();
        this.port = channel.currentPort();
        this.channel = channel;
    }

    @Override
    public final String host() {
        return this.host;
    }

    @Override
    public final int port() {
        return this.port;
    }

    @Override
    public final String key() {
        return this.host + "@@" + this.port;
    }

    @Override
    public final void send(Message message) {
        this.channel.send(message);
    }

    @Override
    public final void close() {
        channel.close();
    }
}
