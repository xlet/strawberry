package cn.w.im.messageBus;

import cn.w.im.core.handlers.HeartbeatRespHandler;
import cn.w.im.core.handlers.JsonMessageDecoder;
import cn.w.im.core.handlers.JsonMessageEncoder;
import cn.w.im.core.handlers.MessageBusHandler;
import cn.w.im.core.plugins.Plugin;
import cn.w.im.core.server.MessageBus;
import cn.w.im.domains.conf.Configuration;
import cn.w.im.plugins.PluginsContainer;
import cn.w.im.utils.spring.SpringContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午10:59.
 * Summary:
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerInitializer.class);

    private MessageBus currentServer;
    private Collection<Plugin> allPlugins;
    private Configuration configuration;

    public ServerInitializer(MessageBus messageBus) {
        this.currentServer = messageBus;
        this.allPlugins = PluginsContainer.all();
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
                new MessageBusHandler(this.currentServer, this.allPlugins));
    }
}
