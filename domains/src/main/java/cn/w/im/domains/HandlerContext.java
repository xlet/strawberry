package cn.w.im.domains;

import cn.w.im.domains.messages.Message;
import cn.w.im.utils.netty.IpAddressProvider;
import io.netty.channel.ChannelHandlerContext;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-3 上午10:43.
 * Summary: handlerContext.
 */
public class HandlerContext {

    /**
     * 当前Mesage.
     */
    private Message message;

    /**
     * 当前的ChannelHandlerContext.
     */
    private ChannelHandlerContext ctx;

    /**
     * 构造函数.
     * @param message 当前message.
     * @param ctx 当前ChannelHandlerContext.
     */
    public HandlerContext(Message message, ChannelHandlerContext ctx) {
        this.message = message;
        this.ctx = ctx;
    }

    /**
     * 获取当前Message.
     * @return 当前Message.
     */
    public Message getMessage() {
        return message;
    }

    /**
     * 获取客户端Ip地址.
     * @return 客户端Ip地址.
     */
    public String getClientIpAddress() {
        return IpAddressProvider.getRemoteIpAddress(ctx);
    }

    /**
     * 获取客户端端口号.
     * @return 客户端端口号.
     */
    public int getClientPort() {
        return IpAddressProvider.getRemotePort(ctx);
    }

    /**
     * 获取连接Context.
     * @return 连接Context.
     */
    public ChannelHandlerContext getCtx() {
        return this.ctx;
    }

    /**
     * 回复客户端消息.
     * @param message 需要回复的消息.
     */
    public void write(Message message) {
        ctx.writeAndFlush(message);
    }

    /**
     * 关闭连接.
     */
    public void close() {
        ctx.close();
    }
}
