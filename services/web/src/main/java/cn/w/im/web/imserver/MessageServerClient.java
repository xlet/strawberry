package cn.w.im.web.imserver;

import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.core.handlers.HeartbeatReqHandler;
import cn.w.im.core.handlers.JsonMessageDecoder;
import cn.w.im.core.handlers.JsonMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
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
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author jackie.
 *         the service link to im server to keep alive.
 */
@Component
public class MessageServerClient extends Thread{

    private static final Log LOG = LogFactory.getLog(MessageServerClient.class);


    private WebMessageHandler webMessageHandler = new WebMessageHandler();
    private boolean started = false;

    public MessageServerClient() {
        init();
        if (!started) {
            this.start();
        }
    }

    @Override
    public void start() {
        super.start();
        LOG.debug("start connect message core.");
        started = true;
        LOG.debug("connected message core.");
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
                                    new HeartbeatReqHandler(true, 30),
                                    webMessageHandler

                            );
                        }
                    });
            ChannelFuture closeFuture = bootstrap.connect("10.0.41.104", 16041).sync().channel().closeFuture().sync();
            closeFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {

                }
            });

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


    private Bootstrap bootstrap;
    EventLoopGroup messageGroup;
    private InetSocketAddress remoteAddress;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, new DefaultThreadFactory("ReconnectThread"));

    private void init() {
        remoteAddress = new InetSocketAddress("10.0.41.104", 16041);
        messageGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
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
                                new HeartbeatReqHandler(true, 30),
                                webMessageHandler
                        );
                    }
                });
    }

    private void doConnect() throws Exception {
        try {
            ChannelFuture channelFuture = bootstrap.connect(remoteAddress).sync();
            channelFuture.channel().closeFuture().sync();
            //release all resources
            LOG.debug("ctx lose, about to reconnect...");
        } finally {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        try {
                            LOG.debug("reconnecting...");
                            doConnect();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void run() {
        try {
            doConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
