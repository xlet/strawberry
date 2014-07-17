package cn.w.im.testClient;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.PluginContext;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.client.LoginResponseMessage;
import cn.w.im.handlers.JsonMessageDecoder;
import cn.w.im.handlers.JsonMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
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

    private final static MessageClientType MESSAGE_CLIENT_TYPE = MessageClientType.WinForm;

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
                input();
            }
        }).start();
    }


    private static void input() {
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


    EventLoopGroup loginGroup = new NioEventLoopGroup();

    private void login(final String id, final String password) {

        try {
            final LoginHandler handler = new LoginHandler(MESSAGE_CLIENT_TYPE, id, password);
            handler.addListener(loginListener);

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loginGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new LengthFieldPrepender(4),
                                    new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4),
                                    new StringEncoder(CharsetUtil.UTF_8),
                                    new StringDecoder(CharsetUtil.UTF_8),
                                    new JsonMessageEncoder(),
                                    new JsonMessageDecoder(),
                                    handler
                            );
                        }
                    });
            bootstrap.connect("10.0.41.102", 17031).sync().channel().closeFuture().sync();
        } catch (Exception ex) {
            loginGroup.shutdownGracefully();
            ex.printStackTrace();
        }
    }

    private HandlerListener loginListener = new HandlerListener() {
        @Override
        public void operationComplete(PluginContext context) {
            if (context.getMessage() instanceof LoginResponseMessage) {
                LoginResponseMessage loginResponseMessage = (LoginResponseMessage) context.getMessage();
                loginGroup.shutdownGracefully();
                if (loginResponseMessage.isSuccess()) {
                    System.out.println("登陆成功!");
                    ConnectToken connectToken = ((LoginResponseMessage) context.getMessage()).getToken();
                    connectMessageServer(connectToken);
                } else {
                    System.out.print("登录失败！");
                    input();
                }
            }
        }
    };

    private EventLoopGroup connectGroup = new NioEventLoopGroup();

    private void connectMessageServer(ConnectToken connectToken) {
        final ConnectHandler handler = new ConnectHandler(connectToken, MESSAGE_CLIENT_TYPE);
        ServerBasic serverBasic = connectToken.getAllocatedMessageServer();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(connectGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new LengthFieldPrepender(4),
                                    new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4),
                                    new StringEncoder(CharsetUtil.UTF_8),
                                    new StringDecoder(CharsetUtil.UTF_8),
                                    new JsonMessageEncoder(),
                                    new JsonMessageDecoder(),
                                    handler
                            );
                        }
                    });
            bootstrap.connect(serverBasic.getHost(), serverBasic.getPort()).sync().channel().closeFuture().sync();
        } catch (Exception ex) {
            connectGroup.shutdownGracefully();
            ex.printStackTrace();
        }
    }
}
