package cn.w.im.core;

import cn.w.im.core.message.Message;

/**
 * channel.
 */
public interface Channel {

    String currentHost();

    int currentPort();

    void send(Message message);

    void close();
}
