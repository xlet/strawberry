package cn.w.im.core.client;

import cn.w.im.core.Channel;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.server.ServerBasic;

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
