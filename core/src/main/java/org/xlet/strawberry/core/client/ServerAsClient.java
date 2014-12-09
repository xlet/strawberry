package org.xlet.strawberry.core.client;

import org.xlet.strawberry.core.Channel;
import org.xlet.strawberry.core.server.ServerBasic;

/**
 * server as client.
 */
public class ServerAsClient extends AbstractClient {

    private ServerBasic basic;

    public ServerAsClient(Channel channel, ServerBasic connectedServer, ServerBasic basic) {
        super(channel, connectedServer);
        this.basic = basic;
    }

    public final ServerBasic getBasic() {
        return basic;
    }
}
