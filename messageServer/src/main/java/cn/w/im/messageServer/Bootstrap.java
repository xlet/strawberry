package cn.w.im.messageServer;

import cn.w.im.domains.conf.Configuration;
import cn.w.im.core.server.MessageServer;
import cn.w.im.utils.spring.SpringContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: JackieHan.
 * Date: 13-11-15 上午11:23.
 * <p/>
 * 启动Im服务.
 */
public class Bootstrap {

    private static Log logger = LogFactory.getLog(Bootstrap.class);
    private static Bootstrap daemon = null;

    /**
     * 启动主函数.
     *
     * @param args 启动参数.
     */
    public static void main(String[] args) {
        if (daemon == null) {
            daemon = new Bootstrap();
        }

        try {
            String command = "start";

            if (args.length > 0) {
                command = args[args.length - 1];
            }

            if (command.endsWith("start")) {
                daemon.init();
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

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Configuration configuration;

    private void init(){
        configuration = SpringContext.context().getBean(Configuration.class);
        MessageServer.current().init(configuration.getHost(),configuration.getPort());
    }

    private void startServer() throws Exception {
        logger.debug("core starting.");

        int serverPort = MessageServer.current().getPort();
        String serverHost = MessageServer.current().getHost();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer());

        ChannelFuture future;

        if (configuration.isDebug()) {
            future = serverBootstrap.bind(serverPort).sync();
        } else {
            future = serverBootstrap.bind(serverHost, serverPort).sync();
        }
        future.addListener(bindFutureListener);
        future.channel().closeFuture().sync();
    }

    private final ChannelFutureListener bindFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (future.isSuccess()) {
                MessageServer.current().start();
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
            MessageServer.current().stop();
            System.exit(0);
        } catch (Exception ex) {
            logger.error("stop error.", ex);
            logger.debug("error stopped.");
            System.exit(1);
        }
    }
}
