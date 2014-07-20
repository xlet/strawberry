package cn.w.im.handlers;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.messages.Message;
import cn.w.im.plugins.Plugin;
import cn.w.im.plugins.init.PluginInitializerFactory;
import cn.w.im.server.LoginServer;
import cn.w.im.utils.netty.IpAddressProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午3:47.
 * Summary:
 */
public class LoginServerHandler extends ChannelInboundHandlerAdapter {

    private List<Plugin> plugins = new ArrayList<Plugin>();

    private final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 构造函数.
     */
    public LoginServerHandler() {
        plugins = PluginInitializerFactory.getInitializer(LoginServer.current().getServerType()).init();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("client linked in");
        LoginServer.current().clientCacheProvider().registerClient(ctx);

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
        //TODO: channel inactive process.
        logger.debug("client channel inactive,remove cached client info.");
        LoginServer.current().clientCacheProvider().removeClient(ctx);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String ip = IpAddressProvider.getRemoteIpAddress(ctx);
        int port = IpAddressProvider.getRemotePort(ctx);
        logger.error("client[" + ip + ":" + port + "] error !", cause);
        //TODO: not cached exception process.
        ctx.close();

    }
}
