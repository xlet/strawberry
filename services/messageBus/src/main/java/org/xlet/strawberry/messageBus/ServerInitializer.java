package org.xlet.strawberry.messageBus;

import org.xlet.strawberry.netty.handlers.JsonMessageDecoder;
import org.xlet.strawberry.netty.handlers.JsonMessageEncoder;
import org.xlet.strawberry.netty.handlers.MessageBusHandler;
import org.xlet.strawberry.core.server.BusServer;
import org.xlet.strawberry.core.config.Configuration;
import org.xlet.strawberry.core.spring.SpringContext;
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
 * DateTime: 14-1-14 上午10:59.
 * Summary:
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerInitializer.class);

    private BusServer currentServer;
    private Configuration configuration;

    public ServerInitializer(BusServer messageBus) {
        this.currentServer = messageBus;
        this.configuration = SpringContext.context().getBean(Configuration.class);
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        if (this.configuration.isNettyInnerLoggerEnable()) {
            ch.pipeline().addLast(new LoggingHandler());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("add netty inner logger handler.");
            }
        }
        ch.pipeline().addLast(
                new LengthFieldPrepender(4),
                new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4),
                new StringEncoder(CharsetUtil.UTF_8),
                new StringDecoder(CharsetUtil.UTF_8),
                new JsonMessageEncoder(),
                new JsonMessageDecoder(),
                //heartbeat
                //new ReadTimeoutHandler(35, TimeUnit.SECONDS),
                //new HeartbeatRespHandler(),
                new MessageBusHandler(this.currentServer));
    }
}
