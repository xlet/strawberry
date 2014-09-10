package cn.w.im.loginServer;


import cn.w.im.domains.conf.Configuration;
import cn.w.im.core.server.LoginServer;
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
 * DateTime: 14-1-8 下午2:52.
 * Summary: 登陆服务器启动入口.
 */
public class Bootstrap {

    /**
     * 日志
     */
    private static Log logger = LogFactory.getLog(Bootstrap.class);

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

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Configuration configuration;
    private LoginServer loginServer;


    private Bootstrap() {
        configuration = SpringContext.context().getBean(Configuration.class);
        this.loginServer = new LoginServer(configuration.getHost(), configuration.getPort());
    }

    private void startServer() throws Exception {
        logger.debug("core starting.");

        String host = this.loginServer.getHost();
        int serverPort = this.loginServer.getPort();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer(this.loginServer));

        ChannelFuture bindFuture;
        if (configuration.isDebug()) {
            bindFuture = serverBootstrap.bind(serverPort).sync();
        } else {
            bindFuture = serverBootstrap.bind(host, serverPort).sync();
        }
        bindFuture.addListener(bindFutureListener);
        bindFuture.channel().closeFuture().sync();
    }

    private ChannelFutureListener bindFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (future.isSuccess()) {
                loginServer.start();
                logger.debug("core started.");
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

            loginServer.stop();
            logger.debug("stopped.");
            System.exit(0);
        } catch (Exception ex) {
            logger.error("stop error.", ex);
            logger.debug("error stopped.");
            System.exit(1);
        }
    }
}
