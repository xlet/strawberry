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
        this.currentServer.clientProvider().registerClient(channel);

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
        //TODO: channel inactive process.
        LOGGER.debug("client channel inactive,remove cached client info.");
        Channel channel = new NettyChannel(ctx);
        this.currentServer.clientProvider().removeClient(channel);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String ip = IpAddressProvider.getRemoteIpAddress(ctx);
        int port = IpAddressProvider.getRemotePort(ctx);
        LOGGER.error("client[" + ip + ":" + port + "] error !", cause);
        //TODO: not cached exception process.
        ctx.close();

    }
}
