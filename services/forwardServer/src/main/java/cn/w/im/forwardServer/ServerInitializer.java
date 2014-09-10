package cn.w.im.forwardServer;

import cn.w.im.core.handlers.ForwardServerHandler;
import cn.w.im.core.handlers.HeartbeatReqHandler;
import cn.w.im.core.handlers.JsonMessageDecoder;
import cn.w.im.core.handlers.JsonMessageEncoder;
import cn.w.im.core.server.ForwardServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;


/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午3:43.
 * Summary:
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private ForwardServer currentServer;

    public ServerInitializer(ForwardServer forwardServer) {
        this.currentServer = forwardServer;
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
                new HeartbeatReqHandler(true, 30),
                new ForwardServerHandler(currentServer));
    }
}
