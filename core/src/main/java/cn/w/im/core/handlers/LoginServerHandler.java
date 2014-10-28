package cn.w.im.core.handlers;

import cn.w.im.core.plugins.DefaultPluginProviderImpl;
import cn.w.im.core.plugins.PluginContext;
import cn.w.im.core.plugins.PluginProvider;
import cn.w.im.core.server.AbstractServer;
import cn.w.im.domains.messages.Message;
import cn.w.im.core.plugins.Plugin;
import cn.w.im.core.server.LoginServer;
import cn.w.im.utils.netty.IpAddressProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午3:47.
 * Summary:
 */
public class LoginServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServerHandler.class);

    private AbstractServer currentServer;

    private PluginProvider pluginProvider;

    /**
     * 构造函数.
     */
    public LoginServerHandler(LoginServer loginServer, Collection<Plugin> plugins) {
        this.currentServer = loginServer;
        this.pluginProvider = new DefaultPluginProviderImpl(plugins);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.debug("client linked in");
        this.currentServer.clientCacheProvider().registerClient(ctx);

        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        PluginContext context = new PluginContext(message, ctx, this.currentServer);
        List<Plugin> plugins = this.pluginProvider.getMatchedPlugins(context);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("matched {} plugins", plugins.size());
        }

        for (Plugin plugin : plugins) {
            plugin.process(context);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //TODO: channel inactive process.
        LOGGER.debug("client channel inactive,remove cached client info.");
        this.currentServer.clientCacheProvider().removeClient(ctx);
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
