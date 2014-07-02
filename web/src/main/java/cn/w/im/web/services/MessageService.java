package cn.w.im.web.services;

import cn.w.im.domains.messages.client.WebNormalMessage;

/**
 * @author jackie.
 */
public interface MessageService {

    /**
     * send message.
     * @param message message.
     */
    void sendMessage(WebNormalMessage message);
}
