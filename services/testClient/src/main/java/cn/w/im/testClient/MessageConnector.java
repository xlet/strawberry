package cn.w.im.testClient;

import cn.w.im.core.handlers.JsonMessageDecoder;
import cn.w.im.core.handlers.JsonMessageEncoder;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.client.ProductType;
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
 * message connector.
 */
public class MessageConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConnector.class);

    private final ClientStarter clientStarter;
    private String token;
    private String memberId;
    private MessageClientType messageClientType;

    private EventLoopGroup connectGroup = new NioEventLoopGroup();
    private ProductType productType;
    private ServerBasic allocatedMessageServer;

    public MessageConnector(ClientStarter clientStarter, String token, String memberId,
                            MessageClientType messageClientType, ProductType productType, ServerBasic allocatedMessageSErver) {
        this.clientStarter = clientStarter;
        this.token = token;
        this.memberId = memberId;
        this.messageClientType = messageClientType;
        this.productType = productType;
        this.allocatedMessageServer = allocatedMessageSErver;
    }

    public void connect() {

        final ConnectHandler handler = new ConnectHandler(this.token, this.memberId, messageClientType, this.productType);
        ServerBasic serverBasic = this.allocatedMessageServer;

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
