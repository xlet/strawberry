package org.xlet.strawberry.netty.handlers;

import org.xlet.strawberry.core.Channel;
import org.xlet.strawberry.core.MessageHandlerContext;
import org.xlet.strawberry.core.server.AbstractServer;
import org.xlet.strawberry.core.message.Message;
import org.xlet.strawberry.core.server.LoginServer;
import org.xlet.strawberry.netty.NettyChannel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午3:47.
 * Summary:
 */
public class LoginServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServerHandler.class);

    private AbstractServer currentServer;

    /**
     * 构造函数.
     */
    public LoginServerHandler(LoginServer loginServer) {
        this.currentServer = loginServer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("client linked in");
        Channel channel = new NettyChannel(ctx);
        this.currentServer.clientProvider().registerClient(channel, this.currentServer);

        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        Channel channel = new NettyChannel(ctx);
        MessageHandlerContext context = new MessageHandlerContext(message, channel, this.currentServer);
        this.currentServer.messageArrived(context);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = new NettyChannel(ctx);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("client channel[host:{},port:{}] inactive,remove cached client info.", channel.host(), channel.port());
        }

        this.currentServer.clientProvider().removeClient(channel);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = new NettyChannel(ctx);

        LOGGER.error("client[" + channel.host() + ":" + channel.port() + "] error !", cause);
    }
}
