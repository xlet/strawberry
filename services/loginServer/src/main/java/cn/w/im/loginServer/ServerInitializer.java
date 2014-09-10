package cn.w.im.loginServer;

import cn.w.im.core.handlers.HeartbeatRespHandler;
import cn.w.im.core.handlers.JsonMessageDecoder;
import cn.w.im.core.handlers.JsonMessageEncoder;
import cn.w.im.core.handlers.LoginServerHandler;
import cn.w.im.core.plugins.Plugin;
import cn.w.im.core.server.LoginServer;
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

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午3:43.
 * Summary:
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private LoginServer currentServer;

    public ServerInitializer(LoginServer currentServer) {
        this.currentServer = currentServer;
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
                //once read time out detected, channel will be disposed
                new ReadTimeoutHandler(35, TimeUnit.SECONDS),
                new HeartbeatRespHandler(),
                new LoginServerHandler(this.currentServer, PluginsContainer.all()));
    }
}
