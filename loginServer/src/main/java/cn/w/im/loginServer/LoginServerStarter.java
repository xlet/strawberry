package cn.w.im.loginServer;


import cn.w.im.domains.conf.Configuration;
import cn.w.im.domains.server.LoginServer;
import cn.w.im.domains.server.ServerType;
import cn.w.im.handlers.MessageBusConnectionHandler;
import cn.w.im.handlers.MessageDecoder;
import cn.w.im.handlers.MessageEncoder;
import cn.w.im.utils.ConfigHelper;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
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
     */
    private void start() {
        logger.info("messageServer Starting!");

        try {
            Properties properties = ConfigHelper.getConfig(this.getClass(), "conf/server.conf");

            Configuration.current().init(properties);

            String hostIp = properties.getProperty("host");
            int port = Integer.parseInt(properties.getProperty("port"));
            String busHost = properties.getProperty("bus.host");
            int busPort = Integer.parseInt(properties.getProperty("bus.port"));
            LoginServer.current().init(hostIp, port, busHost, busPort);

            logger.info("read configuration: loginServer[" + hostIp + ":" + port + "],messageBus[" + busHost + ":" + busPort + "]");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    startServer();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    registerToMessageBus();
                }
            }).start();
        } catch (Exception ex) {
            logger.error("未知错误!", ex);
        }
    }

    /**
     * 启动.
     */
    private void startServer() {

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
            if (Configuration.current().isDebug()) {
                bindFuture = serverBootstrap.bind(serverPort).sync();
            } else {
                bindFuture = serverBootstrap.bind(host, serverPort).sync();
            }
            bindFuture.addListener(bindFutureListener);
            bindFuture.channel().closeFuture().sync();
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private ChannelFutureListener bindFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            LoginServer.current().start();
            logger.info("server started!");
        }
    };

    private void registerToMessageBus() {
        EventLoopGroup clientGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new StringEncoder(CharsetUtil.UTF_8),
                                    new StringDecoder(CharsetUtil.UTF_8),
                                    new MessageEncoder(),
                                    new MessageDecoder(),
                                    new MessageBusConnectionHandler(ServerType.LoginServer)
                            );
                        }
                    });

            ChannelFuture connectFuture = bootstrap.connect(LoginServer.current().getBusHost(), LoginServer.current().getBusPort()).sync();
            connectFuture.channel().closeFuture().sync();
        } catch (Exception ex) {
            logger.info("未知错误!", ex);
            clientGroup.shutdownGracefully();
        }
    }
}
