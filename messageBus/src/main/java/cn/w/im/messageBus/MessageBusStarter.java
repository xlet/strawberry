package cn.w.im.messageBus;

import cn.w.im.domains.server.MessageBus;
import cn.w.im.utils.ConfigHelper;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午9:31.
 * Summary: 消息总线启动入口.
 */
public class MessageBusStarter {

    private Log logger = LogFactory.getLog(this.getClass());

    private boolean debug = false;

    /**
     * 服务启动入口.
     * @param args 参数.
     */
    public static void main(String[] args) {
        new MessageBusStarter().start();
    }

    private void start() {
        try {
            logger.info("server starting");
            Properties properties = ConfigHelper.getConfig(this.getClass(), "config/server.conf");

            debug = Boolean.parseBoolean(properties.getProperty("debug"));

            logger.info("运行为debug模式!");

            String host = properties.getProperty("host");
            int port = Integer.parseInt(properties.getProperty("port"));

            logger.info("read configuration: server[" + host + ":" + port + "]");
            MessageBus.current().init(host, port);

            startServer();

        } catch (Exception ex) {
            logger.error("未知错误!", ex);
        }
    }

    private void startServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());

            ChannelFuture bindFuture;
            if (debug) {
                bindFuture = bootstrap.bind(MessageBus.current().getPort()).sync();
            } else {
                bindFuture = bootstrap.bind(MessageBus.current().getHost(), MessageBus.current().getPort()).sync();
            }
            bindFuture.addListener(bindFutureListener);

            bindFuture.channel().closeFuture().sync();
        } catch (Exception ex) {
            logger.info("未知错误!", ex);
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private ChannelFutureListener bindFutureListener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            MessageBus.current().start();
            logger.info("message bus server started!");
        }
    };


}
