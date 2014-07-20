package cn.w.im.web.imserver;

import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.handlers.JsonMessageDecoder;
import cn.w.im.handlers.JsonMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * @author jackie.
 *         the service link to im server to keep alive.
 */
@Component
public class MessageServerClient {

    private static final Log LOG = LogFactory.getLog(MessageServerClient.class);


    private WebMessageHandler webMessageHandler = new WebMessageHandler();
    private boolean started = false;

    public MessageServerClient() {
        if (!started) {
            this.start();
        }
    }

    private void start() {
        LOG.debug("start connect message server.");
        Runnable webMessageServer = new Runnable() {
            @Override
            public void run() {
                connect();
            }
        };
        Thread messageServerClientThread = new Thread(webMessageServer);
        messageServerClientThread.start();
        started = true;
        LOG.debug("connected message server.");
    }

    private void connect() {
        EventLoopGroup messageGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(messageGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new LengthFieldPrepender(4),
                                    new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4),
                                    new StringEncoder(CharsetUtil.UTF_8),
                                    new StringDecoder(CharsetUtil.UTF_8),
                                    new JsonMessageEncoder(),
                                    new JsonMessageDecoder(),
                                    webMessageHandler
                            );
                        }
                    });
            bootstrap.connect("10.0.41.104", 16041).sync().channel().closeFuture().sync();

        } catch (Exception ex) {
            LOG.error("connect error!", ex);
            messageGroup.shutdownGracefully();
        }
    }

    private ChannelHandlerContext getCtx() {
        ChannelHandlerContext ctx = this.webMessageHandler.getCtx();
        if (ctx == null) {
            throw new RuntimeException("the web message service is down!");
        }
        return ctx;
    }


    public void sendMessage(NormalMessage message) {
        this.getCtx().writeAndFlush(message);
    }
}
