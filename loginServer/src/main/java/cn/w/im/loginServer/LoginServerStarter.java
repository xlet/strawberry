package cn.w.im.loginServer;


import cn.w.im.domains.server.LoginServer;
import cn.w.im.utils.ConfigHelper;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;


/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午2:52.
 * Summary: 登陆服务器启动入口.
 */
public class LoginServerStarter {

    /**
     * 日志
     */
    private Log logger = LogFactory.getLog(this.getClass());

    private boolean debug = false;

    /**
     * 启动主函数.
     *
     * @param args 启动参数.
     */
    public static void main(String[] args) {
        new LoginServerStarter().start();
    }

    /**
     * 启动服务器.
     *
     * @throws Exception 异常信息.
     */
    private void start() {
        logger.info("server Starting!");

        try {
            Properties properties = ConfigHelper.getConfig(this.getClass(), "config/server.conf");

            this.debug = Boolean.parseBoolean(properties.getProperty("debug"));
            logger.info("debug model");

            String hostIp = properties.getProperty("host");
            int port = Integer.parseInt(properties.getProperty("port"));
            String busHost = properties.getProperty("bus.host");
            int busPort = Integer.parseInt(properties.getProperty("bus.port"));
            LoginServer.current().init(hostIp, port, busHost, busPort);

            logger.info("read configuration: loginServer[" + hostIp + ":" + port + "],messageBus[" + busHost + ":" + busPort + "]");
            startListenServerPort();
        } catch (Exception ex) {
            logger.error("未知错误!", ex);
        }
    }

    /**
     * 启动.
     *
     * @throws Exception 异常.
     */
    private void startListenServerPort() throws Exception {

        String host = LoginServer.current().getHost();
        int serverPort = LoginServer.current().getPort();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());
            ChannelFuture bindFuture;
            if (debug) {
                bindFuture = serverBootstrap.bind(serverPort).sync();
            } else {
                bindFuture = serverBootstrap.bind(host, serverPort).sync();
            }
            bindFuture.addListener(bindFutureListener);
            bindFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private ChannelFutureListener bindFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            LoginServer.current().start();
            registerToMessageBus();
        }
    };

    private void registerToMessageBus() {
        EventLoopGroup clientGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new MessageBusClientInitializer());

            ChannelFuture connectFuture = bootstrap.connect(LoginServer.current().getBusHost(), LoginServer.current().getBusPort()).sync();
            connectFuture.addListener(connectFutureListener);
            connectFuture.channel().closeFuture().sync();
        } catch (Exception ex) {
            logger.info("未知错误!", ex);
            clientGroup.shutdownGracefully();
        }
    }

    private ChannelFutureListener connectFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            logger.info("server started!");
        }
    };
}
