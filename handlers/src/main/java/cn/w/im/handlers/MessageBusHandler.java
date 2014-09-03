package cn.w.im.handlers;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.messages.Message;
import cn.w.im.core.server.MessageBus;
import cn.w.im.plugins.Plugin;
import cn.w.im.plugins.init.PluginInitializerFactory;
import cn.w.im.utils.netty.IpAddressProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午11:22.
 * Summary: 消息服务Handler.
 */
public class MessageBusHandler extends ChannelInboundHandlerAdapter {

    private Log logger = LogFactory.getLog(this.getClass());

    private List<Plugin> plugins;

    /**
     * 构造函数.
     */
    public MessageBusHandler() {
        plugins = PluginInitializerFactory.getInitializer(MessageBus.current().getServerType()).init();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MessageBus.current().clientCacheProvider().registerClient(ctx);
        logger.debug("channel active.");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;

        PluginContext context = new PluginContext(message, ctx);
        for (Plugin plugin : plugins) {
            plugin.process(context);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String remoteIp = IpAddressProvider.getRemoteIpAddress(ctx);
        int remotePort = IpAddressProvider.getRemotePort(ctx);
        //TODO:jackie 处理退出, tell all server.
        logger.debug("client[" + remoteIp + ":" + remotePort + "] disconnected!");
        MessageBus.current().clientCacheProvider().removeClient(ctx);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String remoteIp = IpAddressProvider.getRemoteIpAddress(ctx);
        int remotePort = IpAddressProvider.getRemotePort(ctx);
        //TODO: 容错处理
        logger.error("client[" + remoteIp + ":" + remotePort + "] crashed!", cause);
    }
}
