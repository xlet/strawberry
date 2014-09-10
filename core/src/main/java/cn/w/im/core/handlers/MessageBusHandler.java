package cn.w.im.core.handlers;

import cn.w.im.core.plugins.DefaultPluginProviderImpl;
import cn.w.im.core.plugins.PluginContext;
import cn.w.im.core.plugins.PluginProvider;
import cn.w.im.core.server.AbstractServer;
import cn.w.im.domains.messages.Message;
import cn.w.im.core.server.MessageBus;
import cn.w.im.core.plugins.Plugin;
import cn.w.im.utils.netty.IpAddressProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午11:22.
 * Summary: 消息服务Handler.
 */
public class MessageBusHandler extends ChannelInboundHandlerAdapter {

    private Log logger = LogFactory.getLog(this.getClass());

    private AbstractServer currentServer;

    private PluginProvider pluginProvider;

    /**
     * 构造函数.
     */
    public MessageBusHandler(MessageBus messageBus, Collection<Plugin> plugins) {
        this.currentServer = messageBus;
        this.pluginProvider = new DefaultPluginProviderImpl(plugins);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.currentServer.clientCacheProvider().registerClient(ctx);
        logger.debug("channel active.");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;

        PluginContext context = new PluginContext(message, ctx, currentServer);
        List<Plugin> plugins = pluginProvider.getMatchedPlugins(context);
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
        this.currentServer.clientCacheProvider().removeClient(ctx);
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
