package cn.w.im.forwardServer;

import cn.w.im.core.handlers.ForwardServerHandler;
import cn.w.im.core.handlers.HeartbeatReqHandler;
import cn.w.im.core.handlers.JsonMessageDecoder;
import cn.w.im.core.handlers.JsonMessageEncoder;
import cn.w.im.core.server.ForwardServer;
import cn.w.im.domains.conf.Configuration;
import cn.w.im.domains.conf.ForwardConfiguration;
import cn.w.im.utils.spring.SpringContext;
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
