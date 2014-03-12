package cn.w.im.handlers;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.server.MessageServer;
import cn.w.im.domains.messages.Message;
import cn.w.im.plugins.Plugin;
import cn.w.im.plugins.init.PluginInitializerFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 13-11-28 上午9:35.
 * Summary: 负责处理消息的接收和发送.
 */
public class MessageServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 注册的所有插件
     */
    private List<Plugin> plugins;

    /**
     * 构造函数.
     */
    public MessageServerHandler() {
        plugins = PluginInitializerFactory.getInitializer(MessageServer.current().getServerType()).init();
    }

    /**
     * channel Active.
     * @param ctx 当前连接上下文.
     * @throws Exception 异常.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    /**
     * channel read.
     * @param ctx 当前上下文.
     * @param msg 消息.
     * @throws Exception 异常.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        message.setReceivedTime(new Date().getTime());

        HandlerContext context = new HandlerContext(message, ctx);
        for (Plugin plugin : plugins) {
            plugin.process(context);
        }
    }

    /**
     * exceptionCaught.
     * @param ctx 当前上下文.
     * @param cause 异常信息.
     * @throws Exception 异常.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String ipAddress = remoteAddress.getHostString();
        int port = remoteAddress.getPort();

        MessageServer.current().removeClient(ipAddress, port);
    }
}
