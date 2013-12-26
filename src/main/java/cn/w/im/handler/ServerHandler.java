package cn.w.im.handler;

import cn.w.im.message.LoginMessage;
import cn.w.im.message.LoginResponseMessage;
import cn.w.im.message.Message;
import cn.w.im.message.MessageType;
import cn.w.im.plugins.message.MessageHandler;
import cn.w.im.plugins.message.MessageHandlerFactory;
import cn.w.im.server.ClientInfo;
import cn.w.im.server.ServerInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.util.Date;

/**
 * Creator: JackieHan
 * DateTime: 13-11-28 上午9:35
 * Summary: 负责处理消息的接收和发送
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private ServerInfo serverInfo;

    public ServerHandler(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        message.setReceivedTime(new Date());
        MessageHandler messageHandler= MessageHandlerFactory.getHandler(message.getMessageType(),ctx,serverInfo);
        messageHandler.process(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        InetSocketAddress remoteAddress=(InetSocketAddress)ctx.channel().remoteAddress();
        String ipAddress=remoteAddress.getHostString();
        int port=remoteAddress.getPort();

        this.serverInfo.removeClient(ipAddress,port);
    }
}
