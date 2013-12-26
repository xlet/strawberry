package cn.w.im.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * User: JackieHan
 * Date: 13-11-15 上午11:23
 *
 * 启动Im服务
 */
public class ServerStarter {

    /**
     * 启动主函数
     *
     * @param args 启动参数
     */
    public static void main(String[] args)
    {
        try {
            new ServerStarter().Start();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void Start() throws Exception{
        System.out.println("server Started!");

        ServerInfo thisServer=new ServerInfo();

        StartListenServerPort(thisServer);
    }


    private void StartListenServerPort(ServerInfo serverInfo) throws Exception{
        int serverPort= serverInfo.getServerPort();

        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();

        try{
        ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer(serverInfo));

        serverBootstrap.bind(serverPort).sync().channel().closeFuture().sync();
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
