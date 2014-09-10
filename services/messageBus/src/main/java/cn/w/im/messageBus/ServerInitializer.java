package cn.w.im.messageBus;

import cn.w.im.core.handlers.HeartbeatRespHandler;
import cn.w.im.core.handlers.JsonMessageDecoder;
import cn.w.im.core.handlers.JsonMessageEncoder;
import cn.w.im.core.handlers.MessageBusHandler;
import cn.w.im.core.server.MessageBus;
import cn.w.im.plugins.PluginsContainer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午10:59.
 * Summary:
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private MessageBus currentServer;

    public ServerInitializer(MessageBus messageBus) {
        this.currentServer = messageBus;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(
                new LoggingHandler(),
                new LengthFieldPrepender(4),
                new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4),
                new StringEncoder(CharsetUtil.UTF_8),
                new StringDecoder(CharsetUtil.UTF_8),
                new JsonMessageEncoder(),
                new JsonMessageDecoder(),
                //heartbeat
                new ReadTimeoutHandler(35, TimeUnit.SECONDS),
                new HeartbeatRespHandler(),
                new MessageBusHandler(this.currentServer, PluginsContainer.all()));
    }
}
