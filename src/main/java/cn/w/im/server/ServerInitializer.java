package cn.w.im.server;

import cn.w.im.handler.MessageDecoder;
import cn.w.im.handler.MessageEncoder;
import cn.w.im.handler.ServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * Creator: JackieHan
 * DateTime: 13-11-28 上午9:24
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private ServerInfo serverInfo;

    public ServerInitializer(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(
                new StringEncoder(CharsetUtil.UTF_8),
                new StringDecoder(CharsetUtil.UTF_8),
                new MessageEncoder(),
                new MessageDecoder(),
                new ServerHandler(serverInfo)
        );
    }
}
