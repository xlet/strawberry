package cn.w.im.handlers;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.server.MessageBus;
import cn.w.im.plugins.Plugin;
import cn.w.im.plugins.init.PluginInitializerFactory;
import cn.w.im.utils.netty.IpAddressProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
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
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        message.setReceivedTime(new Date());

        HandlerContext context = new HandlerContext(message, ctx);
        for (Plugin plugin : plugins) {
            logger.info("开始执行: " + plugin.description());
            plugin.process(context);
            logger.info("结束执行: " + plugin.description());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String remoteIp = IpAddressProvider.getRemoteIpAddress(ctx);
        int remotePort = IpAddressProvider.getRemotePort(ctx);
        Client crashClient = MessageBus.current().getClient(remoteIp, remotePort);
        //TODO: 容错处理

        MessageBus.current().removeClient(remoteIp, remotePort);
        super.exceptionCaught(ctx, cause);
    }
}