package cn.w.im.web.services;


import cn.w.im.domains.messages.client.NormalMessage;

import java.util.List;

/**
 * @author jackie.
 */
public interface MessageService {

    /**
     * send message.
     *
     * @param message message.
     */
    List<NormalMessage> sendMessage(NormalMessage message);

    /**
     * get not received messages.
     *
     * @param loginId login id.
     * @param toId    to id.
     * @return not received messages.
     */
    List<NormalMessage> getNotReceivedMessage(String loginId, String toId);

    /**
     * get not received messages.
     *
     * @param loginId login id.
     * @return not received messages.
     */
    List<NormalMessage> getNotReceivedMessage(String loginId);
}
