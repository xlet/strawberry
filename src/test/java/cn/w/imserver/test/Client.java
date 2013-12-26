package cn.w.imserver.test;

import cn.w.im.handler.MessageDecoder;
import cn.w.im.handler.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * Creator: JackieHan
 * DateTime: 13-12-16 上午10:24
 */
public class Client {

    public static void main(String[] args) throws Exception {

        Runtime runtime=Runtime.getRuntime();
        Thread thread=new Thread(new ShutDownListener());
        runtime.addShutdownHook(thread);

        Scanner sc=new Scanner(System.in);

        System.out.println("请输入用户名:");
        String id=sc.nextLine();

        String password=new InputMasking().getPassword("请输入密码:");

        connectToServer(id,password);
    }

    private static void connectToServer(final String id,final String password) throws InterruptedException{
        EventLoopGroup clientGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(
                                new StringEncoder(CharsetUtil.UTF_8),
                                new StringDecoder(CharsetUtil.UTF_8),
                                new MessageEncoder(),
                                new MessageDecoder(),
                                new ClientHandler(id,password)
                        );
                    }
                });
        bootstrap.connect("127.0.0.1", 16000).sync().channel().closeFuture().sync();
    }
}
