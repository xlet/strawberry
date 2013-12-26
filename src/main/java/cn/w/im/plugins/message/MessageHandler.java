package cn.w.im.plugins.message;

import cn.w.im.message.Message;
import cn.w.im.server.ServerInfo;
import io.netty.channel.ChannelHandlerContext;

/**
 * Creator: JackieHan
 * DateTime: 13-12-24 下午2:44
 */
public interface MessageHandler {
    /**
     * 初始化MessageHandler
     * @param ctx 当前ChannelHandlerContext
     * @param serverInfo 当前ServerInfo
     */
    void init(ChannelHandlerContext ctx,ServerInfo serverInfo);

    /**
     * 处理消息
     * @param message 消息
     */
    void process(Message message);
}
