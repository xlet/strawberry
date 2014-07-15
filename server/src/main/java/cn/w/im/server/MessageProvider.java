package cn.w.im.server;

import cn.w.im.domains.messages.client.NormalMessage;

import java.util.List;

/**
 * message provider.
 */
public interface MessageProvider {

    /**
     * get not received messages.
     *
     * @param from the message send from.
     * @param to   the message send to.
     * @return all not received messages.
     */
    List<NormalMessage> getNotReceivedMessage(String from, String to);
}
