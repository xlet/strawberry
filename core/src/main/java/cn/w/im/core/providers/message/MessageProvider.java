package cn.w.im.core.providers.message;

import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.messages.Message;
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

    /**
     * send message.
     *
     * @param host    host.
     * @param port    port.
     * @param message message.
     */
    void send(String host, int port, Message message);

    /**
     * send to all the specified type of server.
     *
     * @param serverType server type.
     * @param message    message.
     */
    void send(ServerType serverType, Message message);

    /**
     * send to specified server.
     *
     * @param serverBasic server basic.
     * @param message     message.
     */
    void send(ServerBasic serverBasic, Message message);

    /**
     * send to logged with login id message client.
     *
     * @param loginId login id.
     * @param message message.
     */
    void send(String loginId, Message message);
}
