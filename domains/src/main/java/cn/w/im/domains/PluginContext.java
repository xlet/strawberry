package cn.w.im.domains;

import cn.w.im.domains.messages.Message;
import cn.w.im.utils.netty.IpAddressProvider;
import io.netty.channel.ChannelHandlerContext;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-3 上午10:43.
 * Summary: plugin context.
 */
public class PluginContext {

    private Message message;

    private String currentHost;

    private int currentPort;


    /**
     * 构造函数.
     * @param message 当前message.
     * @param ctx 当前ChannelHandlerContext.
     */
    public PluginContext(Message message, ChannelHandlerContext ctx) {
        this.message = message;
        this.currentHost = IpAddressProvider.getRemoteIpAddress(ctx);
        this.currentPort = IpAddressProvider.getRemotePort(ctx);
    }

    /**
     * 获取当前Message.
     * @return 当前Message.
     */
    public Message getMessage() {
        return message;
    }

    /**
     * get current host.
     * @return current host.
     */
    public String getCurrentHost() {
        return currentHost;
    }

    /**
     * get current port.
     * @return current port.
     */
    public int getCurrentPort() {
        return currentPort;
    }
}
