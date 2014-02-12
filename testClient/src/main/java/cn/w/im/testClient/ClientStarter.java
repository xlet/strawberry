package cn.w.im.testClient;

import cn.w.im.domains.HandlerContext;
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
public class ClientStarter {

    /**
     * 启动主函数.
     *
     * @param args 启动参数.
     * @throws Exception 异常.
     */
    public static void main(String[] args) throws Exception {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Scanner sc = new Scanner(System.in);
                    System.out.println("请输入用户名:");
                    String id = sc.nextLine();
                    String password = new InputMasking().getPassword("请输入密码:");
                    new ClientStarter().login(id, password);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void login(final String id, final String password) throws Exception {

        final ClientHandler handler = new ClientHandler(id, password);
        handler.addListener(loginListener);

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
                                handler
                        );
                    }
                });
        bootstrap.connect("10.0.40.20", 17021).sync().channel().closeFuture().sync();
    }

    private HandlerListener loginListener = new HandlerListener() {
        @Override
        public void operationComplete(HandlerContext context) {
            System.out.println("登陆成功!");
        }
    };
}
