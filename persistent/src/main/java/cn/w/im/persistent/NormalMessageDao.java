package cn.w.im.persistent;

import cn.w.im.domains.messages.client.NormalMessage;

import java.util.List;

/**
 * normal message dao.
 */
public interface NormalMessageDao {

    /**
     * get member's offline message list.
     *
     * @param memberId member id.
     * @return message list.
     */
    List<NormalMessage> getOfflineMessages(String memberId);

    /**
     * get offline message list which are from sent to to.
     *
     * @param from from member id.
     * @param to   to member id.
     * @return message list.
     */
    List<NormalMessage> getOfflineMessages(String from, String to);

    /**
     * set message which send to given member id forwarded.
     * @param memberId member id.
     * @return update count.
     */
    int setMessageForwarded(String memberId);

    /**
     * set message which from send to to forwarded.
     * @param from from member id.
     * @param to to member id.
     * @return update count.
     */
    int setMessageForwarded(String from, String to);
}
