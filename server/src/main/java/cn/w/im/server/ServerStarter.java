package cn.w.im.server;

import cn.w.im.domains.server.MessageServer;
import io.netty.bootstrap.ServerBootstrap;
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
public class ServerStarter {


    /**
     * 日志
     */
    private static Log log = LogFactory.getLog(ServerStarter.class);

    /**
     * 启动主函数.
     *
     * @param args 启动参数.
     */
    public static void main(String[] args) {
        try {
            new ServerStarter().start();
        } catch (Exception ex) {
            log.error("发生错误", ex);
        }
    }

    /**
     * 启动服务器.
     *
     * @throws Exception 异常信息.
     */
    private void start() throws Exception {
        log.info("server Started!");

        startListenServerPort();
    }


    /**
     * 启动.
     * @throws Exception 异常.
     */
    private void startListenServerPort() throws Exception {
        int serverPort = MessageServer.current().getServerPort();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());

            serverBootstrap.bind(serverPort).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
