package cn.w.im.netty.handlers;

import cn.w.im.core.Channel;
import cn.w.im.core.MessageHandlerContext;
import cn.w.im.core.server.AbstractServer;
import cn.w.im.core.message.Message;
import cn.w.im.core.server.MessageServer;
import cn.w.im.netty.NettyChannel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creator: JackieHan.
 * DateTime: 13-11-28 上午9:35.
 * Summary: 负责处理消息的接收和发送.
 */
public class MessageServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServerHandler.class);

    private AbstractServer currentServer;

    /**
     * 构造函数.
     *
     * @param messageServer message server.
     */
    public MessageServerHandler(MessageServer messageServer) {
        this.currentServer = messageServer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //TODO:jackie refuse message client connect.
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
        LOGGER.debug("client[host:{},port{}] inactive!", channel.currentHost(), channel.currentPort());
        this.currentServer.clientProvider().removeClient(channel);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = new NettyChannel(ctx);
        this.currentServer.clientProvider().removeClient(channel);
        LOGGER.error("client[" + channel.currentHost() + ":" + channel.currentPort() + "] error !", cause);
    }
}
