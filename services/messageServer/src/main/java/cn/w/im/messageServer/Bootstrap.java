package cn.w.im.messageServer;

import cn.w.im.core.config.Configuration;
import cn.w.im.core.server.MessageServer;
import cn.w.im.core.spring.SpringContext;
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
 * User: JackieHan.
 * Date: 13-11-15 上午11:23.
 * <p/>
 * 启动Im服务.
 */
public class Bootstrap {

    private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);
    private static Bootstrap daemon = null;

    /**
     * 启动主函数.
     *
     * @param args 启动参数.
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
    private MessageServer messageServer;

    private Bootstrap() throws UnknownHostException {
        configuration = SpringContext.context().getBean(Configuration.class);
        this.messageServer = new MessageServer(configuration.getOuterHost(), configuration.getPort());
    }

    private void startServer() throws Exception {
        logger.debug("core starting.");

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        int serverPort = this.messageServer.getPort();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer(this.messageServer));

        ChannelFuture future;

        future = serverBootstrap.bind(this.configuration.getBind(), serverPort).sync();
        logger.debug("listening port:" + this.messageServer.getPort());
        future.addListener(bindFutureListener);
        future.channel().closeFuture().sync();
    }

    private final ChannelFutureListener bindFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (future.isSuccess()) {
                messageServer.start();
                logger.debug("core started!");
            }
        }
    };

    private synchronized void stopServer() {
        try {
            logger.debug("stopping.");

            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }
            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
            }
            messageServer.stop();
            System.exit(0);
        } catch (Exception ex) {
            logger.error("stop error.", ex);
            logger.debug("error stopped.");
            System.exit(1);
        }
    }
}
