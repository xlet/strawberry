package cn.w.im.handlers;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.server.LoginServer;
import cn.w.im.plugins.Plugin;
import cn.w.im.plugins.init.PluginInitializerFactory;
import cn.w.im.utils.netty.IpAddressProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Date;
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
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        message.setReceivedTime(new Date().getTime());

        HandlerContext context = new HandlerContext(message, ctx);
        for (Plugin plugin : plugins) {
            logger.debug("processing: " + plugin.description());
            plugin.process(context);
            logger.debug("processed: " + plugin.description());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String ip = IpAddressProvider.getRemoteIpAddress(ctx);
        int port = IpAddressProvider.getRemotePort(ctx);
        logger.error("client[" + ip + ":" + port + "] error !", cause);
    }
}
