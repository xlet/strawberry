package cn.w.im.messageBus;

import cn.w.im.domains.conf.Configuration;
import cn.w.im.core.server.MessageBus;
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
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午9:31.
 * Summary: 消息总线启动入口.
 */
public final class Bootstrap {

    private static Log logger = LogFactory.getLog(Bootstrap.class);

    private static Bootstrap daemon = null;

    static {

    }

    /**
     * 服务启动入口.
     *
     * @param args 参数.
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

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Configuration configuration;

    private void init() {
        configuration = (Configuration) SpringContext.context().getBean("serverConfig");
        MessageBus.current().init(configuration.getHost(), configuration.getPort());
    }

    private void startServer() throws Exception {
        logger.info("message bus core starting");

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer());

        ChannelFuture bindFuture;
        if (configuration.isDebug()) {
            bindFuture = bootstrap.bind(MessageBus.current().getPort()).sync();
        } else {
            bindFuture = bootstrap.bind(MessageBus.current().getHost(), MessageBus.current().getPort()).sync();
        }
        bindFuture.addListener(bindFutureListener);

        bindFuture.channel().closeFuture().sync();

    }

    private ChannelFutureListener bindFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            MessageBus.current().start();
            logger.debug("message bus core started!");
        }
    };

    private void stopServer() throws Exception {
        try {
            logger.debug("core stopping.");
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }
            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
            }
            MessageBus.current().stop();
            logger.debug("core stopped.");
            System.exit(0);
        } catch (Throwable t) {
            logger.error("stopped error.", t);
            logger.debug("error stopped.");
            System.exit(1);
        }
    }


}
