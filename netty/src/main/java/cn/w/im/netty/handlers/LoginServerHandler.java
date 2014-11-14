package cn.w.im.netty.handlers;

import cn.w.im.core.Channel;
import cn.w.im.core.MessageHandlerContext;
import cn.w.im.core.server.AbstractServer;
import cn.w.im.core.message.Message;
import cn.w.im.core.server.LoginServer;
import cn.w.im.netty.IpAddressProvider;
import cn.w.im.netty.NettyChannel;
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
        this.currentServer.clientProvider().removeClient(channel);

        LOGGER.debug("client channel[host:{},port:{}] inactive,remove cached client info.", channel.currentHost(), channel.currentPort());
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = new NettyChannel(ctx);
        this.currentServer.clientProvider().removeClient(channel);
        LOGGER.error("client[" + channel.currentHost() + ":" + channel.currentPort() + "] error !", cause);
        ctx.close();
    }
}
