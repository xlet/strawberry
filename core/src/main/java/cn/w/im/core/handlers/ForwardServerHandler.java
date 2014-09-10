package cn.w.im.core.handlers;

import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.forward.ForwardResponseMessage;
import cn.w.im.exceptions.ListeningThreadStartErrorException;
import cn.w.im.core.server.ForwardServer;
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

    private ForwardServer currentServer;

    public ForwardServerHandler(ForwardServer currentServer){
        this.currentServer = currentServer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //add
        logger.debug("channelActive!");
        currentServer.connected(ctx);
        try {
            waitConnected();
            currentServer.requestServerBasic(ctx);
        } catch (ListeningThreadStartErrorException ex) {
            logger.error(ex.getMessage(), ex);
        }
        super.channelActive(ctx);
    }

    private synchronized void waitConnected() throws Exception {
        while (true) {
            logger.debug("waiting for core and bus all connected.");
            this.wait(500);
            if (currentServer.isConnectedError()) {
                throw new ListeningThreadStartErrorException();
            }
            if (currentServer.allConnected()) {
                break;
            }
        }
    }

    private synchronized void waitResponded() throws Exception {
        while (true) {
            logger.debug("waiting for core and bus all responded.");
            this.wait(500);
            if (currentServer.allResponded()) {
                break;
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof ForwardResponseMessage) {
                ForwardResponseMessage responseMessage = (ForwardResponseMessage) msg;
                currentServer.receivedResponse(responseMessage, ctx);
                waitResponded();
                currentServer.ready(ctx);
            } else if (msg instanceof Message) {
                Message message = (Message) msg;
                currentServer.forwardMessage(message, ctx);
            }else{
                ctx.fireChannelRead(msg);
            }
        } catch (Exception ex) {
            logger.error("forward core crashed.");
            logger.error(ex.getMessage(), ex);
            currentServer.crashed();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.error("forward core stopped.");
        currentServer.serverStopped();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("core crashed.", cause);
        currentServer.serverCrashed();
    }
}
