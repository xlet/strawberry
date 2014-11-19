package cn.w.im.netty;

import cn.w.im.core.Channel;
import cn.w.im.core.message.Message;
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
