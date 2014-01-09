package cn.w.im.server;

import cn.w.im.handlers.MessageDecoder;
import cn.w.im.handlers.MessageEncoder;
import cn.w.im.handlers.MessageServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * Creator: JackieHan.
 * DateTime: 13-11-28 上午9:24.
 * Summary: 服务器初始化.
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 初始化channel.
     * @param socketChannel socketChannel.
     * @throws Exception Exception.
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(
                new StringEncoder(CharsetUtil.UTF_8),
                new StringDecoder(CharsetUtil.UTF_8),
                new MessageEncoder(),
                new MessageDecoder(),
                new MessageServerHandler()
        );
    }
}
