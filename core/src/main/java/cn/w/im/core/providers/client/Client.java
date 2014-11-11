package cn.w.im.core.providers.client;

import cn.w.im.core.message.Message;

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


}
