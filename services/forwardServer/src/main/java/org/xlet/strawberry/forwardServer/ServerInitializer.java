package org.xlet.strawberry.forwardServer;

import org.xlet.strawberry.netty.handlers.ForwardServerHandler;
import org.xlet.strawberry.netty.handlers.JsonMessageDecoder;
import org.xlet.strawberry.netty.handlers.JsonMessageEncoder;
import org.xlet.strawberry.core.server.ForwardServer;
import org.xlet.strawberry.core.config.ForwardConfiguration;
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
 * DateTime: 14-1-8 下午3:43.
 * Summary: forward server initializer.
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerInitializer.class);

    private ForwardServer currentServer;

    private ForwardConfiguration forwardConfiguration;

    public ServerInitializer(ForwardServer forwardServer) {
        this.currentServer = forwardServer;
        this.forwardConfiguration = SpringContext.context().getBean(ForwardConfiguration.class);
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        if (this.forwardConfiguration.isNettyInnerLoggerEnable()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("add netty inner logger handler.");
            }
            ch.pipeline().addLast(new LoggingHandler());
        }
        ch.pipeline().addLast(
                new LengthFieldPrepender(4),
                new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4),
                new StringEncoder(CharsetUtil.UTF_8),
                new StringDecoder(CharsetUtil.UTF_8),
                new JsonMessageEncoder(),
                new JsonMessageDecoder(),
                //heartbeat
                //new HeartbeatReqHandler(true, 30),
                new ForwardServerHandler(currentServer));
    }
}
