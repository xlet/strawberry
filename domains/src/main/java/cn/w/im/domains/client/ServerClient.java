package cn.w.im.domains.client;

import cn.w.im.domains.ServerBasic;
import io.netty.channel.ChannelHandlerContext;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午3:04.
 * Summary: server client.
 */
public class ServerClient extends Client {

    private ServerBasic serverBasic;

    /**
     * constructor.
     *
     * @param serverBasic server basic.
     */
    public ServerClient(ChannelHandlerContext context, ServerBasic serverBasic) {
        super(context);
        this.serverBasic = serverBasic;
    }

    /**
     * get server basic info.
     *
     * @return server basic.
     */
    public ServerBasic getServerBasic() {
        return this.serverBasic;
    }
}
