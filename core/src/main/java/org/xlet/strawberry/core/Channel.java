package org.xlet.strawberry.core;

import org.xlet.strawberry.core.message.Message;

/**
 * channel.
 */
public interface Channel {

    String host();

    int port();

    void send(Message message);

    void close();
}
