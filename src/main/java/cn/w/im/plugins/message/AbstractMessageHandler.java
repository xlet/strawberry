package cn.w.im.plugins.message;

import cn.w.im.server.ServerInfo;
import io.netty.channel.ChannelHandlerContext;

/**
 * Creator: JackieHan
 * DateTime: 13-12-25 上午10:21
 */
public abstract class AbstractMessageHandler implements MessageHandler {

    private ChannelHandlerContext ctx;

    private ServerInfo serverInfo;

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    @Override
    public void init(ChannelHandlerContext ctx, ServerInfo serverInfo) {
        this.ctx=ctx;
        this.serverInfo=serverInfo;
    }
}
