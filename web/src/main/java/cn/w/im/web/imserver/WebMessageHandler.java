package cn.w.im.web.imserver;

import cn.w.im.domains.client.MessageClientType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jackie.
 */
public class WebMessageHandler extends ChannelInboundHandlerAdapter {

    private final static MessageClientType MESSAGE_CLIENT_TYPE = MessageClientType.Web;
    /**
     * 日志
     */
    private final static Log LOG = LogFactory.getLog(WebMessageHandler.class);

    private ChannelHandlerContext ctx;

    public WebMessageHandler() {
    }

    /**
     * get channel handler context.
     *
     * @return channel handler context.
     */
    public ChannelHandlerContext getCtx() {
        return this.ctx;
    }


    /**
     * channelActive.
     *
     * @param ctx 当前连接上下文.
     * @throws Exception 异常.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        LOG.debug("channel active!");
    }

    /**
     * channelRead.
     *
     * @param ctx 上下文.
     * @param msg 消息.
     * @throws Exception 异常.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("channel error!", cause);
        ctx.close();
    }
}
