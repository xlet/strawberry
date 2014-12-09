package org.xlet.strawberry.core.client;

import org.xlet.strawberry.core.Channel;
import org.xlet.strawberry.core.message.Message;
import org.xlet.strawberry.core.server.ServerBasic;

/**
 * client interface.
 */
public interface Client {

    /**
     * get client host.
     *
     * @return client host.
     */
    String host();

    /**
     * get client port.
     *
     * @return client port.
     */
    int port();

    /**
     * get client key.
     *
     * @return get client key.
     */
    String key();

    /**
     * send message.
     *
     * @param message message.
     */
    void send(Message message);

    /**
     * close client.
     */
    void close();

    /**
     * connected server basic.
     *
     * @return connected server basic.
     */
    ServerBasic connectedServer();

    /**
     * get channel.
     *
     * @return channel.
     */
    Channel channel();

}
