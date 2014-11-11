package cn.w.im.netty.handlers;

import cn.w.im.core.MessageHandlerContext;
import cn.w.im.core.message.Message;
import cn.w.im.core.message.forward.ForwardResponseMessage;
import cn.w.im.core.exception.ListeningThreadStartErrorException;
import cn.w.im.core.server.ForwardServer;
import cn.w.im.netty.NettyChannel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 上午11:29.
 * Summary: 消息总线连接Handler.
 */
public class ForwardServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ForwardServer currentServer;

    public ForwardServerHandler(ForwardServer currentServer) {
        this.currentServer = currentServer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //add
        logger.debug("channelActive!");
        NettyChannel channel = new NettyChannel(ctx);
        currentServer.connected(channel);
        try {
            waitConnected();
            currentServer.requestServerBasic(channel);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        super.channelActive(ctx);
    }

    private synchronized void waitConnected() throws Exception {
        while (true) {
            logger.debug("waiting for server and bus all connected.");
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
            logger.debug("waiting for server and bus all responded.");
            this.wait(500);
            if (currentServer.allResponded()) {
                break;
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        NettyChannel channel = new NettyChannel(ctx);
        try {
            if (msg instanceof ForwardResponseMessage) {
                ForwardResponseMessage responseMessage = (ForwardResponseMessage) msg;
                currentServer.receivedResponse(responseMessage, channel);
                waitResponded();
                currentServer.ready(channel);
            } else if (msg instanceof Message) {
                Message message = (Message) msg;
                currentServer.forwardMessage(message, channel);
            } else {
                ctx.fireChannelRead(msg);
            }
        } catch (Exception ex) {
            logger.error("forward crashed.");
            logger.error(ex.getMessage(), ex);
            currentServer.crashed();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.error("forward stopped.");
        currentServer.serverStopped();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("core crashed.", cause);
        currentServer.serverCrashed();
    }
}
