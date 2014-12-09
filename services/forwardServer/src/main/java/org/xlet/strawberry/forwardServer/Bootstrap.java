package org.xlet.strawberry.forwardServer;

import org.xlet.strawberry.core.config.ForwardConfiguration;
import org.xlet.strawberry.core.server.ForwardServer;
import org.xlet.strawberry.core.spring.SpringContext;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午2:52.
 * Summary: 转发服务启动入口.
 */
public class Bootstrap {

    /**
     * 日志
     */
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
                command = args[0];
            }

            if (command.endsWith("start")) {
                if (args.length == 5) {
                    String serverHost = args[1];
                    int serverPort = Integer.parseInt(args[2]);
                    String busHost = args[3];
                    int busPort = Integer.parseInt(args[4]);
                    daemon = new Bootstrap(serverHost, serverPort, busHost, busPort);
                } else {
                    daemon = new Bootstrap();
                }
                daemon.startServer();
            } else if (command.equals("stop")) {
                logger.warn("Bootstrap: command [" + command + "] does not exist.");
            }
        } catch (Throwable t) {
            logger.error("unknown error!", t);
            System.exit(1);
        }
    }

    private ForwardServer forwardServer = null;

    private ForwardConfiguration forwardConfiguration;

    private final EventLoopGroup messageBusClientGroup = new NioEventLoopGroup();
    private final EventLoopGroup serverClientGroup = new NioEventLoopGroup();
    private boolean connectingServer = false;
    private boolean connectingBus = false;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(2, new DefaultThreadFactory("ReconnectThread"));

    private Bootstrap(String serverHost, int serverPort, String busHost, int busPort) {
        this.forwardServer = new ForwardServer(busHost, busPort, serverHost, serverPort);
    }

    private Bootstrap() {
        this.forwardConfiguration = SpringContext.context().getBean(ForwardConfiguration.class);
        this.forwardServer = new ForwardServer(forwardConfiguration.getBusHost(), forwardConfiguration.getBusPort(),
                forwardConfiguration.getServerHost(), forwardConfiguration.getServerPort());
    }

    private void startServer() throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connectingServer = true;
                    connectServer();
                } catch (Exception ex) {
                    forwardServer.connectedError();
                    logger.error("connect server error!", ex);
                    connectingServer = false;
                    stopServer();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connectingBus = true;
                    connectMessageBus();
                } catch (Exception ex) {
                    forwardServer.connectedError();
                    logger.error("connect message bus error.", ex);
                    connectingBus = false;
                    stopServer();
                }
            }
        }).start();
    }

    private void connectServer() throws InterruptedException {
        logger.debug("connect server starting.");
        try {
            io.netty.bootstrap.Bootstrap bootstrap = new io.netty.bootstrap.Bootstrap();
            bootstrap.group(this.serverClientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ServerInitializer(forwardServer));

            ChannelFuture connectFuture = bootstrap.connect(forwardServer.getServerHost(), forwardServer.getServerPort()).sync();
            connectFuture.addListener(connectionServerFutureListener);
            connectFuture.channel().closeFuture().sync();
        } finally {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        connectServer();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private ChannelFutureListener connectionServerFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            connectingServer = false;
            logger.debug("connect server completed.");
        }
    };


    private void connectMessageBus() throws InterruptedException {
        logger.debug("connect message bus starting.");
        try {
            io.netty.bootstrap.Bootstrap bootstrap = new io.netty.bootstrap.Bootstrap();
            bootstrap.group(messageBusClientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ServerInitializer(forwardServer));

            ChannelFuture connectFuture = bootstrap.connect(forwardServer.getBusHost(), forwardServer.getBusPort()).sync();
            connectFuture.addListener(connectionFutureListener);
            connectFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        connectMessageBus();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private ChannelFutureListener connectionFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            connectingBus = false;
            logger.debug("connect message bus completed.");
        }
    };

    private synchronized void stopServer() {
        try {
            logger.debug("stopping.");
            if (forwardServer.isConnectedError()) {
                waitStarted();
            }

            this.messageBusClientGroup.shutdownGracefully();
            messageBusClientGroup.shutdownGracefully();
            if (forwardServer.isConnectedError()) {
                logger.debug("start error.stopped.");
                System.exit(1);
            } else {
                logger.debug("manual stopped.");
                System.exit(0);
            }
        } catch (Exception ex) {
            logger.error("stop error.", ex);
            logger.debug("error stopped.");
            System.exit(1);
        }
    }

    private synchronized void waitStarted() throws Exception {
        long waitStartDate = new Date().getTime();
        while (true && waitStartDate + 20000 >= new Date().getTime()) {
            logger.debug("wait starting is done.");
            this.wait(500);
            if (!connectingServer && !connectingBus) {
                break;
            }
        }
    }
}
