package cn.w.im.loginServer;


import cn.w.im.domains.server.LoginServer;
import io.netty.bootstrap.ServerBootstrap;
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

        int serverPort = LoginServer.current().getServerPort();

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
