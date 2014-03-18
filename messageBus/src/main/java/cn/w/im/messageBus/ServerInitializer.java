package cn.w.im.messageBus;

import cn.w.im.handlers.JsonMessageDecoder;
import cn.w.im.handlers.JsonMessageEncoder;
import cn.w.im.handlers.MessageBusHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午10:59.
 * Summary:
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(
                new LengthFieldPrepender(4),
                new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4),
                new StringEncoder(CharsetUtil.UTF_8),
                new StringDecoder(CharsetUtil.UTF_8),
                new JsonMessageEncoder(),
                new JsonMessageDecoder(),
                new MessageBusHandler());
    }
}
