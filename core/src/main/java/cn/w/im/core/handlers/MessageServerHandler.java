package cn.w.im.core.handlers;

import cn.w.im.core.plugins.DefaultPluginProviderImpl;
import cn.w.im.core.plugins.PluginContext;
import cn.w.im.core.plugins.PluginProvider;
import cn.w.im.core.server.AbstractServer;
import cn.w.im.domains.messages.Message;
import cn.w.im.core.plugins.Plugin;
import cn.w.im.core.server.MessageServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 13-11-28 上午9:35.
 * Summary: 负责处理消息的接收和发送.
 */
public class MessageServerHandler extends ChannelInboundHandlerAdapter {

    private final Log logger = LogFactory.getLog(this.getClass());

    private AbstractServer currentServer;

    private PluginProvider pluginProvider;

    /**
     * 构造函数.
     *
     * @param messageServer message server.
     * @param plugins       all plugins.
     */
    public MessageServerHandler(MessageServer messageServer, Collection<Plugin> plugins) {
        this.currentServer = messageServer;
        this.pluginProvider = new DefaultPluginProviderImpl(plugins);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //TODO:jackie 拒绝链接
        this.currentServer.clientCacheProvider().registerClient(ctx);
        //TODO:if host has registered
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;

        PluginContext context = new PluginContext(message, ctx, this.currentServer);
        List<Plugin> matchedPlugins = pluginProvider.getMatchedPlugins(context);
        for (Plugin plugin : matchedPlugins) {
            plugin.process(context);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //TODO:jackie 退出处理
        this.currentServer.clientCacheProvider().removeClient(ctx);
        super.channelInactive(ctx);
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
