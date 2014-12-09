package org.xlet.strawberry.netty;

import org.xlet.strawberry.core.Channel;
import org.xlet.strawberry.core.message.Message;
import io.netty.channel.ChannelHandlerContext;

/**
 * netty channel implement.
 */
public class NettyChannel implements Channel {

    private ChannelHandlerContext context;

    public NettyChannel(ChannelHandlerContext context) {
        this.context = context;
    }

    @Override
    public String host() {
        return IpAddressProvider.getRemoteIpAddress(this.context);
    }

    @Override
    public int port() {
        return IpAddressProvider.getRemotePort(context);
    }

    @Override
    public void send(Message message) {
        this.context.writeAndFlush(message);
    }

    @Override
    public void close() {
        this.context.close();
    }
}
