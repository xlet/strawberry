package cn.w.im.core.providers.client;

import cn.w.im.core.Channel;
import cn.w.im.core.server.ServerBasic;

/**
 * server as client.
 */
public class ServerAsClient extends AbstractClient {

    private ServerBasic basic;

    public ServerAsClient(Channel channel, ServerBasic basic) {
        super(channel);
        this.basic = basic;
    }

    public final ServerBasic getBasic() {
        return basic;
    }
}
