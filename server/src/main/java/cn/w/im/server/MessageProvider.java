package cn.w.im.server;

import cn.w.im.domains.messages.client.NormalMessage;

import java.util.List;

/**
 * message provider.
 */
public interface MessageProvider {

    /**
     * get member's offline message list.
     *
     * @param memberId member id.
     * @return message list.
     */
    List<NormalMessage> getOfflineMessages(String memberId);

    /**
     * set message which send to given member id forwarded.
     * @param memberId member id.
     * @return update count.
     */
    int setMessageForwarded(String memberId);
}
