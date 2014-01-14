package cn.w.im.testClient;

import cn.w.im.handlers.MessageDecoder;
import cn.w.im.handlers.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-16 上午10:24.
 * Summary: 客户端main Class.
 */
public class Client {

    /**
     * 启动主函数.
     * @param args 启动参数.
     * @throws Exception 异常.
     */
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.println("请输入用户名:");
        String id = sc.nextLine();

        String password = new InputMasking().getPassword("请输入密码:");

        login(id, password);

        connectToServer(id, password);
    }

    private static void login(final String  id, final String password) throws Exception {
        EventLoopGroup clientGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline channelPipeline = socketChannel.pipeline().addLast(
                                new StringEncoder(CharsetUtil.UTF_8),
                                new StringDecoder(CharsetUtil.UTF_8),
                                new MessageEncoder(),
                                new MessageDecoder(),
                                new ClientHandler(id, password)
                        );
                    }
                });
        bootstrap.connect("127.0.0.1", 18000).sync().channel().closeFuture().sync();
    }

    /**
     * 连接服务器.
     * @param id 登陆id.
     * @param password 登陆密码.
     * @throws InterruptedException 异常.
     */
    private static void connectToServer(final String id, final String password) throws InterruptedException {
        EventLoopGroup clientGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline channelPipeline = socketChannel.pipeline().addLast(
                                new StringEncoder(CharsetUtil.UTF_8),
                                new StringDecoder(CharsetUtil.UTF_8),
                                new MessageEncoder(),
                                new MessageDecoder(),
                                new ClientHandler(id, password)
                        );
                    }
                });
        bootstrap.connect("127.0.0.1", 16000).sync().channel().closeFuture().sync();
    }
}
