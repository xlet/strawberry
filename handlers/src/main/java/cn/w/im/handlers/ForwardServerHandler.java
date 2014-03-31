package cn.w.im.handlers;

import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.forward.ForwardResponseMessage;
import cn.w.im.exceptions.ListeningThreadStartErrorException;
import cn.w.im.server.ForwardServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 上午11:29.
 * Summary: 消息总线连接Handler.
 */
public class ForwardServerHandler extends ChannelInboundHandlerAdapter {

    private Log logger = LogFactory.getLog(this.getClass());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ForwardServer.current().connected(ctx);
        try {
            waitConnected();
            ForwardServer.current().requestServerBasic(ctx);
        } catch (ListeningThreadStartErrorException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private synchronized void waitConnected() throws Exception {
        while (true) {
            logger.debug("waiting for server and bus all connected.");
            this.wait(500);
            if (ForwardServer.current().isConnectedError()) {
                throw new ListeningThreadStartErrorException();
            }
            if (ForwardServer.current().allConnected()) {
                break;
            }
        }
    }

    private synchronized void waitResponded() throws Exception {
        while (true) {
            logger.debug("waiting for server and bus all responded.");
            this.wait(500);
            if (ForwardServer.current().allResponded()) {
                break;
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof ForwardResponseMessage) {
                ForwardResponseMessage responseMessage = (ForwardResponseMessage) msg;
                ForwardServer.current().receivedResponse(responseMessage, ctx);
                waitResponded();
                ForwardServer.current().ready(ctx);
            } else if (msg instanceof Message) {
                Message message = (Message) msg;
                ForwardServer.current().forwardMessage(message, ctx);
            }
        } catch (Exception ex) {
            logger.error("forward server crashed.");
            logger.error(ex.getMessage(), ex);
            ForwardServer.current().crashed();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.error("forward server stopped.");
        ForwardServer.current().serverStopped();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("server crashed.", cause);
        ForwardServer.current().serverCrashed();
    }
}
