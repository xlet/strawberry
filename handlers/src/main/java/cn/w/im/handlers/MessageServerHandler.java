package cn.w.im.handlers;

import cn.w.im.domains.PluginContext;
import cn.w.im.server.MessageServer;
import cn.w.im.domains.messages.Message;
import cn.w.im.plugins.Plugin;
import cn.w.im.plugins.init.PluginInitializerFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 13-11-28 上午9:35.
 * Summary: 负责处理消息的接收和发送.
 */
public class MessageServerHandler extends ChannelInboundHandlerAdapter {

    private List<Plugin> plugins;
    private final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 构造函数.
     */
    public MessageServerHandler() {
        plugins = PluginInitializerFactory.getInitializer(MessageServer.current().getServerType()).init();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //TODO:jackie 拒绝链接
        MessageServer.current().clientCacheProvider().registerClient(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        message.setReceivedTime(new Date().getTime());

        PluginContext context = new PluginContext(message, ctx);
        for (Plugin plugin : plugins) {
            logger.info("processing: " + plugin.description());
            plugin.process(context);
            logger.info("processed: " + plugin.description());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //TODO:jackie 退出处理
        MessageServer.current().clientCacheProvider().removeClient(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String ipAddress = remoteAddress.getHostString();
        int port = remoteAddress.getPort();
        //TODO:jackie 异常处理.
        logger.error("client[" + ipAddress + ":" + port + "] error !", cause);
    }
}
