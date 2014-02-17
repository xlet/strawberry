package cn.w.im.handlers;

import cn.w.im.domains.messages.ServerRegisterMessage;
import cn.w.im.domains.messages.RequestLinkedClientsMessage;
import cn.w.im.domains.server.LoginServer;
import cn.w.im.domains.server.MessageServer;
import cn.w.im.domains.server.ServerInstance;
import cn.w.im.domains.server.ServerType;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 上午11:29.
 * Summary: 消息总线连接Handler.
 */
public class MessageBusConnectionHandler extends ChannelInboundHandlerAdapter {

    private Log logger = LogFactory.getLog(this.getClass());

    private ServerType connectionServerType;

    /**
     * 构造函数.
     *
     * @param connectionServerType 连接服务类型.
     */
    public MessageBusConnectionHandler(ServerType connectionServerType) {
        if (connectionServerType == ServerType.MessageBus) {
            throw new NotSupportedServerTypeException(connectionServerType);
        }
        this.connectionServerType = connectionServerType;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        switch (connectionServerType) {
            case MessageServer:
                MessageServer.current().setForwardContext(ctx);
                registerToMessageBus(ctx);
                getOtherMessageServerLinkedClients(ctx);
                break;
            case LoginServer:
                LoginServer.current().setForwardContext(ctx);
                registerToMessageBus(ctx);
                break;
            default:
                throw new NotSupportedServerTypeException(connectionServerType);
        }
    }

    private void registerToMessageBus(ChannelHandlerContext ctx) throws Exception {
        ServerRegisterMessage registerMessage = new ServerRegisterMessage(ServerInstance.current(connectionServerType).getServerBasic(), connectionServerType);
        ctx.writeAndFlush(registerMessage);
    }

    private void getOtherMessageServerLinkedClients(ChannelHandlerContext ctx) throws Exception {
        RequestLinkedClientsMessage requestLinkedClientsMessage = new RequestLinkedClientsMessage(ServerInstance.current(connectionServerType).getServerBasic());
        ctx.writeAndFlush(requestLinkedClientsMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        forwardToServer(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //TODO:jackie 容错处理.
    }

    private void forwardToServer(final Object message) {

        //TODO: jackie 是否有其他方法，不用创建线程组.
        EventLoopGroup clientGroup = new NioEventLoopGroup();

        try {

            String serverHost = ServerInstance.current(connectionServerType).getHost();
            int serverPort = ServerInstance.current(connectionServerType).getPort();

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new StringDecoder(CharsetUtil.UTF_8),
                                    new StringEncoder(CharsetUtil.UTF_8),
                                    new MessageDecoder(),
                                    new MessageEncoder(),
                                    new ForwardToServerHandler(message)
                            );
                        }
                    });
            bootstrap.connect(serverHost, serverPort).sync().channel().closeFuture().sync();
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            clientGroup.shutdownGracefully();
        }
    }

    class ForwardToServerHandler extends ChannelInboundHandlerAdapter {
        private Object msg;

        public ForwardToServerHandler(Object msg) {
            this.msg = msg;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ctx.writeAndFlush(msg);
            ctx.close();
        }
    }
}