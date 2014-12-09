package org.xlet.strawberry.messageBus;

import org.xlet.strawberry.core.server.BusServer;
import org.xlet.strawberry.core.config.Configuration;
import org.xlet.strawberry.core.spring.SpringContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午9:31.
 * Summary: 消息总线启动入口.
 */
public final class Bootstrap {

    private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    private static Bootstrap daemon = null;

    static {

    }

    /**
     * 服务启动入口.
     *
     * @param args 参数.
     */
    public static void main(String[] args) {
        try {
            String command = "start";

            if (args.length > 0) {
                command = args[args.length - 1];
            }

            if (command.endsWith("start")) {
                daemon = new Bootstrap();
                daemon.startServer();
            } else if (command.equals("stop")) {
                daemon.stopServer();
            } else {
                logger.warn("Bootstrap: command [" + command + "] does not exist.");
            }
        } catch (Throwable t) {
            logger.error("unknown error!", t);
            System.exit(1);
        }
    }

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Configuration configuration;
    private BusServer messageBus;

    private Bootstrap() throws UnknownHostException {
        configuration = (Configuration) SpringContext.context().getBean("serverConfig");
        this.messageBus = new BusServer(configuration.getOuterHost(), configuration.getPort());
    }

    private void startServer() throws Exception {
        logger.info("message bus starting");

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer(this.messageBus));

        ChannelFuture bindFuture;
        bindFuture = bootstrap.bind(this.configuration.getBind(), this.messageBus.getPort()).sync();
        logger.debug("listening port:" + this.messageBus.getPort());
        bindFuture.addListener(bindFutureListener);

        bindFuture.channel().closeFuture().sync();

    }

    private ChannelFutureListener bindFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            messageBus.start();
            logger.debug("message bus started!");
        }
    };

    private void stopServer() throws Exception {
        try {
            logger.debug("message bus stopping.");
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }
            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
            }
            messageBus.stop();
            logger.debug("message bus stopped.");
            System.exit(0);
        } catch (Throwable t) {
            logger.error("stopped error.", t);
            logger.debug("error stopped.");
            System.exit(1);
        }
    }
}
