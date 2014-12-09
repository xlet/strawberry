package org.xlet.strawberry.core.client;

import org.xlet.strawberry.core.Channel;
import org.xlet.strawberry.core.member.BasicMember;
import org.xlet.strawberry.core.server.ServerBasic;

/**
 * message client.
 */
public class MessageClient extends AbstractClient {

    private String clientId;

    private BasicMember member;

    private MessageClientType clientType;

    public MessageClient(Channel channel, ServerBasic connectedServer, BasicMember member, MessageClientType clientType, String clientId) {
        super(channel, connectedServer);
        this.clientId = clientId;
        this.member = member;
        this.clientType = clientType;
    }

    public String getClientId() {
        return clientId;
    }

    public final BasicMember getMember() {
        return member;
    }

    public final MessageClientType getClientType() {
        return clientType;
    }
}
