package cn.w.im.netty.handlers;

import cn.w.im.core.Channel;
import cn.w.im.core.MessageHandlerContext;
import cn.w.im.core.server.AbstractServer;
import cn.w.im.core.server.BusServer;
import cn.w.im.core.message.Message;
import cn.w.im.netty.IpAddressProvider;
import cn.w.im.netty.NettyChannel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午11:22.
 * Summary: 消息服务Handler.
 */
public class MessageBusHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageBusHandler.class);

    private AbstractServer currentServer;

    /**
     * 构造函数.
     */
    public MessageBusHandler(BusServer messageBus) {
        this.currentServer = messageBus;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = new NettyChannel(ctx);
        this.currentServer.clientProvider().registerClient(channel);
        LOGGER.debug("client linked in");
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
        String remoteIp = IpAddressProvider.getRemoteIpAddress(ctx);
        int remotePort = IpAddressProvider.getRemotePort(ctx);
        //TODO:jackie 处理退出, tell all server.
        LOGGER.debug("client[" + remoteIp + ":" + remotePort + "] disconnected!");
        Channel channel = new NettyChannel(ctx);
        this.currentServer.clientProvider().removeClient(channel);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String remoteIp = IpAddressProvider.getRemoteIpAddress(ctx);
        int remotePort = IpAddressProvider.getRemotePort(ctx);
        //TODO: 容错处理
        LOGGER.error("client[" + remoteIp + ":" + remotePort + "] crashed!", cause);
    }
}
