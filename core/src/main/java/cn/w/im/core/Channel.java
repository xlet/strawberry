package cn.w.im.core;

import cn.w.im.core.message.Message;

/**
 * channel.
 */
public interface Channel {

    String host();

    int port();

    void send(Message message);

    void close();
}
