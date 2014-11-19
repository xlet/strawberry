package cn.w.im.core.client;

import cn.w.im.core.Channel;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.server.ServerBasic;

/**
 * message client.
 */
public class MessageClient extends AbstractClient {

    private BasicMember member;

    private MessageClientType clientType;

    public MessageClient(Channel channel, ServerBasic connectedServer, BasicMember member, MessageClientType clientType) {
        super(channel, connectedServer);
        this.member = member;
        this.clientType = clientType;
    }

    public final BasicMember getMember() {
        return member;
    }

    public final MessageClientType getClientType() {
        return clientType;
    }
}
