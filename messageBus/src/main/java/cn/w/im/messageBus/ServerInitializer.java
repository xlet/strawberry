package cn.w.im.messageBus;

import cn.w.im.handlers.MessageBusHandler;
import cn.w.im.handlers.MessageDecoder;
import cn.w.im.handlers.MessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
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
        ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8),
                new StringDecoder(CharsetUtil.UTF_8),
                new MessageEncoder(),
                new MessageDecoder(),
                new MessageBusHandler());
    }
}
