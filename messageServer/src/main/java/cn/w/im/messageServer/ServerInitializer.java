package cn.w.im.messageServer;

import cn.w.im.handlers.JsonMessageDecoder;
import cn.w.im.handlers.JsonMessageEncoder;
import cn.w.im.handlers.MessageServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
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
     *
     * @param socketChannel socketChannel.
     * @throws Exception Exception.
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(
                new LengthFieldPrepender(4),
                new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4),
                new StringEncoder(CharsetUtil.UTF_8),
                new StringDecoder(CharsetUtil.UTF_8),
                new JsonMessageEncoder(),
                new JsonMessageDecoder(),
                new MessageServerHandler()
        );
    }
}
