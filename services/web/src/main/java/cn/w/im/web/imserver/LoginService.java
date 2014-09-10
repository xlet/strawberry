package cn.w.im.web.imserver;

import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.client.LoginResponseMessage;
import cn.w.im.core.handlers.JsonMessageDecoder;
import cn.w.im.core.handlers.JsonMessageEncoder;
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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jackie.
 */
public class LoginService {

    private final static MessageClientType MESSAGE_CLIENT_TYPE = MessageClientType.Web;
    private final static Log LOGGER = LogFactory.getLog(LoginService.class);

    public LoginResponseMessage login(final String loginId) {

        final LoginHandler handler = new LoginHandler(MESSAGE_CLIENT_TYPE,loginId);
        new Runnable() {
            @Override
            public void run() {
                startLogin(handler,loginId);
            }
        }.run();

        while (true){
            try {
                if (handler.isCompleted()) {
                    return handler.getResponseMessage();
                }
                this.wait(20);
            }catch (InterruptedException e){
                LOGGER.error(e);
            }
        }
    }

    private void startLogin(final LoginHandler loginHandler,String loginId) {
        EventLoopGroup loginGroup = new NioEventLoopGroup();
        try {
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
                                    loginHandler
                            );
                        }
                    });
            bootstrap.connect("10.0.40.20", 17021).sync().channel().closeFuture().sync();
        } catch (Exception ex) {
            loginGroup.shutdownGracefully();
            ex.printStackTrace();
        }
    }
}
