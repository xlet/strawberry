package cn.w.im.loginServer;


import cn.w.im.domains.conf.Configuration;
import cn.w.im.server.LoginServer;
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
        configuration= SpringContext.context().getBean(Configuration.class);
        LoginServer.current().init(configuration.getHost(),configuration.getPort());
    }

    private void startServer() throws Exception {
        logger.debug("server starting.");

        String host = LoginServer.current().getHost();
        int serverPort = LoginServer.current().getPort();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer());

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
                LoginServer.current().start();
                logger.debug("server started.");
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

            LoginServer.current().stop();
            logger.debug("stopped.");
            System.exit(0);
        } catch (Exception ex) {
            logger.error("stop error.", ex);
            logger.debug("error stopped.");
            System.exit(1);
        }
    }
}
