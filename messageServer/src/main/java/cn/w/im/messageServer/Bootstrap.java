package cn.w.im.messageServer;

import cn.w.im.domains.conf.Configuration;
import cn.w.im.domains.server.MessageServer;
import cn.w.im.domains.server.ServerType;
import cn.w.im.handlers.JsonMessageDecoder;
import cn.w.im.handlers.JsonMessageEncoder;
import cn.w.im.handlers.MessageBusConnectionHandler;
import cn.w.im.utils.ConfigHelper;
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
                daemon.loadConfig();
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
    private final EventLoopGroup clientGroup = new NioEventLoopGroup();
    private boolean serverStarting = false;
    private boolean connecting = false;
    private boolean startError = false;


    private void loadConfig() throws Exception {
        logger.debug("loading config.");

        Properties properties = ConfigHelper.getConfig(Bootstrap.class, "conf/server.conf");
        Configuration.current().init(properties);

        String hostIp = properties.getProperty("host");
        int port = Integer.parseInt(properties.getProperty("port"));
        String busHost = properties.getProperty("bus.host");
        int busPort = Integer.parseInt(properties.getProperty("bus.port"));
        MessageServer.current().init(hostIp, port, busHost, busPort);

        logger.debug("read configuration: messageServer[" + hostIp + ":" + port + "]");
        logger.debug("read configuration: messageBus[" + busHost + ":" + busPort + "]");
        logger.debug("loaded config.");
    }

    private void startServer() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverStarting = true;
                    start();
                } catch (Exception ex) {
                    startError = true;
                    logger.error("start server error.", ex);
                    serverStarting = false;
                    stopServer();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connecting = true;
                    connectMessageBus();
                } catch (Exception ex) {
                    startError = true;
                    logger.error("register to message bus error.", ex);
                    connecting = false;
                    stopServer();
                }
            }
        }).start();
    }

    private void start() throws Exception {
        logger.debug("server starting.");

        int serverPort = MessageServer.current().getPort();
        String serverHost = MessageServer.current().getHost();

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
        future.addListener(bindFutureListener);
        future.channel().closeFuture().sync();
    }

    private final ChannelFutureListener bindFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (future.isSuccess()) {
                serverStarting = false;
                MessageServer.current().start();
                logger.debug("server started!");
            }
        }
    };

    private void connectMessageBus() throws Exception {
        logger.debug("connect message bus server starting.");

        String busHost = MessageServer.current().getBusHost();
        int busPort = MessageServer.current().getBusPort();

        io.netty.bootstrap.Bootstrap bootstrap = new io.netty.bootstrap.Bootstrap();
        bootstrap.group(clientGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                new StringEncoder(CharsetUtil.UTF_8),
                                new StringDecoder(CharsetUtil.UTF_8),
                                new JsonMessageEncoder(),
                                new JsonMessageDecoder(),
                                new MessageBusConnectionHandler(ServerType.MessageServer)
                        );
                    }
                });
        ChannelFuture connectFuture = bootstrap.connect(busHost, busPort).sync();
        connectFuture.addListener(connectFutureListener);
        connectFuture.channel().closeFuture().sync();
    }

    private final ChannelFutureListener connectFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (future.isSuccess()) {
                connecting = false;
                MessageServer.current().connectedBusServer();
                logger.debug("connect message bus server completed.");
            }
        }
    };

    private synchronized void stopServer() {
        try {
            logger.debug("stopping.");
            if (startError) {
                waitStarted();
            }

            MessageServer.current().stop();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

            MessageServer.current().disconnectedBusServer();
            clientGroup.shutdownGracefully();

            if (startError) {
                logger.debug("start error.stopped.");
            } else {
                logger.debug("normal stopped.");
            }
            System.exit(0);
        } catch (Exception ex) {
            logger.error("stop error.", ex);
            logger.debug("error stopped.");
            System.exit(1);
        }
    }

    private synchronized void waitStarted() throws Exception {
        while (true) {
            if (serverStarting || connecting) {
                logger.debug("wait starting is done.");
                this.wait(200);
            } else {
                break;
            }
        }
    }
}
