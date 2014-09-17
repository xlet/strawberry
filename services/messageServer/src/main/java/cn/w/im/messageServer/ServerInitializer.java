package cn.w.im.messageServer;

import cn.w.im.core.handlers.HeartbeatRespHandler;
import cn.w.im.core.handlers.JsonMessageDecoder;
import cn.w.im.core.handlers.JsonMessageEncoder;
import cn.w.im.core.handlers.MessageServerHandler;
import cn.w.im.core.plugins.Plugin;
import cn.w.im.core.server.MessageServer;
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

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Creator: JackieHan.
 * DateTime: 13-11-28 上午9:24.
 * Summary: 服务器初始化.
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private MessageServer currentServer;
    private Collection<Plugin> allPlugins;

    public ServerInitializer(MessageServer messageServer) {
        this.currentServer = messageServer;
        this.allPlugins = PluginsContainer.all();
    }

    /**
     * 初始化channel.
     *
     * @param socketChannel socketChannel.
     * @throws Exception Exception.
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(
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
                new MessageServerHandler(this.currentServer, this.allPlugins));
    }
}
