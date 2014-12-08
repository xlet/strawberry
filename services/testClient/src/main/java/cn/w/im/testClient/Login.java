package cn.w.im.testClient;

import cn.w.im.core.MessageHandlerContext;
import cn.w.im.netty.handlers.JsonMessageDecoder;
import cn.w.im.netty.handlers.JsonMessageEncoder;
import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.message.client.LoginResponseMessage;
import cn.w.im.core.ProductType;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * login
 */
public class Login {

    private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);

    private static final String LOGIN_SERVER_HOST = "10.0.40.38";
    private static final int LOGIN_SERVER_PORT = 17021;

    private final ClientStarter clientStarter;
    private String loginId;
    private String password;
    private MessageClientType messageClientType;
    private ProductType productType;


    private EventLoopGroup loginGroup = new NioEventLoopGroup();

    public Login(ClientStarter clientStarter, String loginId, String password, MessageClientType messageClientType, ProductType productType) {
        this.clientStarter = clientStarter;
        this.loginId = loginId;
        this.password = password;
        this.messageClientType = messageClientType;
        this.productType = productType;
    }

    public void login() {
        try {
            final ClientHandler handler = new ClientHandler(this.productType, this.messageClientType, loginId, password);
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
            bootstrap.connect(LOGIN_SERVER_HOST, LOGIN_SERVER_PORT).sync().channel().closeFuture().sync();
        } catch (Exception ex) {
            loginGroup.shutdownGracefully();
            ex.printStackTrace();
        }
    }

    private HandlerListener loginListener = new HandlerListener() {
        @Override
        public void operationComplete(MessageHandlerContext context) {
            if (context.getMessage() instanceof LoginResponseMessage) {
                LoginResponseMessage loginResponseMessage = (LoginResponseMessage) context.getMessage();
                loginGroup.shutdownGracefully();
                if (loginResponseMessage.isSuccess()) {
                    LOGGER.debug("登陆成功!");
                    LoginResponseMessage responseMessage = (LoginResponseMessage) context.getMessage();
                    String token = responseMessage.getToken();
                    String memberId = responseMessage.getMemberId();
                    String messageHost = responseMessage.getMessageHost();
                    int messagePort = responseMessage.getMessagePort();
                    clientStarter.loginSuccess(token, memberId, messageHost, messagePort);
                } else {
                    clientStarter.loginError();
                }
            }
        }
    };
}
