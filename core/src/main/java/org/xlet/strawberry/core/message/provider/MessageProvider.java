package org.xlet.strawberry.core.message.provider;

import org.xlet.strawberry.core.Channel;
import org.xlet.strawberry.core.server.ServerBasic;
import org.xlet.strawberry.core.server.ServerType;
import org.xlet.strawberry.core.member.BasicMember;
import org.xlet.strawberry.core.message.Message;
import org.xlet.strawberry.core.message.client.NormalMessage;

import java.util.Collection;

/**
 * message provider.
 */
public interface MessageProvider {

    /**
     * get member's offline message list.
     *
     * @param owner owner.
     * @return message list.
     */
    Collection<NormalMessage> getOfflineMessages(BasicMember owner);

    /**
     * set message which send to given member id forwarded.
     *
     * @param owner owner.
     */
    void setMessageForwarded(BasicMember owner);

    /**
     * sent message.
     *
     * @param channel current  channel.
     * @param message message.
     */
    void send(Channel channel, Message message);

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
     * send to message client by member.
     *
     * @param member  member.
     * @param message message.
     */
    void send(BasicMember member, Message message);
}
