package cn.w.im.messageServer;

import cn.w.im.netty.handlers.JsonMessageDecoder;
import cn.w.im.netty.handlers.JsonMessageEncoder;
import cn.w.im.netty.handlers.MessageServerHandler;
import cn.w.im.core.server.MessageServer;
import cn.w.im.core.config.Configuration;
import cn.w.im.core.spring.SpringContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creator: JackieHan.
 * DateTime: 13-11-28 上午9:24.
 * Summary: 服务器初始化.
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerInitializer.class);

    private MessageServer currentServer;
    private Configuration configuration;

    public ServerInitializer(MessageServer messageServer) {
        this.currentServer = messageServer;
        this.configuration = SpringContext.context().getBean(Configuration.class);
    }

    /**
     * 初始化channel.
     *
     * @param socketChannel socketChannel.
     * @throws Exception Exception.
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        if (this.configuration.isNettyInnerLoggerEnable()) {
            socketChannel.pipeline().addLast(new LoggingHandler());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("add netty inner logger handler.");
            }
        }
        socketChannel.pipeline().addLast(
                //new LoggingHandler(),
                new LengthFieldPrepender(4),
                new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4),
                new StringEncoder(CharsetUtil.UTF_8),
                new StringDecoder(CharsetUtil.UTF_8),
                new JsonMessageEncoder(),
                new JsonMessageDecoder(),
                //heartbeat
                //new ReadTimeoutHandler(35, TimeUnit.SECONDS),
                //new HeartbeatRespHandler(),
                new MessageServerHandler(this.currentServer));
    }
}
