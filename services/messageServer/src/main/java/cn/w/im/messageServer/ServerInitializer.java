package cn.w.im.messageServer;

import cn.w.im.core.handlers.HeartbeatRespHandler;
import cn.w.im.core.handlers.JsonMessageDecoder;
import cn.w.im.core.handlers.JsonMessageEncoder;
import cn.w.im.core.handlers.MessageServerHandler;
import cn.w.im.core.plugins.Plugin;
import cn.w.im.core.server.MessageServer;
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
 * DateTime: 13-11-28 上午9:24.
 * Summary: 服务器初始化.
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerInitializer.class);

    private MessageServer currentServer;
    private Collection<Plugin> allPlugins;
    private Configuration configuration;

    public ServerInitializer(MessageServer messageServer) {
        this.currentServer = messageServer;
        this.allPlugins = PluginsContainer.all();
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
        if (this.configuration.isNettyInnerLoggerEnable()){
            socketChannel.pipeline().addLast(new LoggingHandler());
            if (LOGGER.isDebugEnabled()){
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
                new MessageServerHandler(this.currentServer, this.allPlugins));
    }
}
