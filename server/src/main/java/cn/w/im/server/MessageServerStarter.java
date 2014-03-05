package cn.w.im.server;

import cn.w.im.domains.conf.Configuration;
import cn.w.im.domains.server.MessageServer;
import cn.w.im.domains.server.ServerType;
import cn.w.im.handlers.MessageDecoder;
import cn.w.im.handlers.MessageEncoder;
import cn.w.im.handlers.MessageBusConnectionHandler;
import cn.w.im.utils.ConfigHelper;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.bootstrap.Bootstrap;
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
 * User: JackieHan.
 * Date: 13-11-15 上午11:23.
 * <p/>
 * 启动Im服务.
 */
public class MessageServerStarter {

    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * 启动主函数.
     *
     * @param args 启动参数.
     */
    public static void main(String[] args) {
        new MessageServerStarter().start();
    }

    private void start() {

        logger.info("server Starting!");
        try {
            Properties properties = ConfigHelper.getConfig(MessageServerStarter.class, "conf/server.conf");

            //配置信息初始化.
            Configuration.current().init(properties);

            String hostIp = properties.getProperty("host");
            int port = Integer.parseInt(properties.getProperty("port"));
            String busHost = properties.getProperty("bus.host");
            int busPort = Integer.parseInt(properties.getProperty("bus.port"));
            MessageServer.current().init(hostIp, port, busHost, busPort);

            logger.info("read configuration: messageServer[" + hostIp + ":" + port + "]");
            logger.info("read configuration: messageBus[" + busHost + ":" + busPort + "]");

            Thread startServerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        startServer();
                    } catch (Exception ex) {
                        logger.error(ex);
                    }
                }
            });

            Thread messageBusConnectionThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        connectMessageBus();
                    } catch (Exception ex) {
                        logger.error(ex);
                    }
                }
            });

            startServerThread.start();
            messageBusConnectionThread.start();
        } catch (Exception ex) {
            logger.error("未知错误", ex);
        }
    }

    private void startServer() throws Exception {
        int serverPort = MessageServer.current().getPort();
        String serverHost = MessageServer.current().getHost();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());

            ChannelFuture future;

            if (Configuration.current().isDebug()) {
                future = serverBootstrap.bind(serverPort).sync();
            } else {
                future = serverBootstrap.bind(serverHost, serverPort).sync();
            }
            future.addListener(registerToMessageBus);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private final ChannelFutureListener registerToMessageBus = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (future.isSuccess()) {
                MessageServer.current().start();
                logger.info("server started!");
            }
        }
    };

    /**
     * 注册到登陆服务.
     */
    private void connectMessageBus() {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {
            String busHost = MessageServer.current().getBusHost();
            int busPort = MessageServer.current().getBusPort();

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
                                    new MessageBusConnectionHandler(ServerType.MessageServer)
                            );
                        }
                    });
            ChannelFuture connectFuture = bootstrap.connect(busHost, busPort).sync();
            connectFuture.channel().closeFuture().sync();
        } catch (Exception ex) {
            logger.error("未知错误!", ex);
        } finally {
            clientGroup.shutdownGracefully();
        }
    }
}
