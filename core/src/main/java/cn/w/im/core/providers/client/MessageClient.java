package cn.w.im.core.providers.client;

import cn.w.im.core.Channel;
import cn.w.im.core.MessageClientType;
import cn.w.im.core.member.BasicMember;

/**
 * message client.
 */
public class MessageClient extends AbstractClient {

    private BasicMember member;

    private MessageClientType clientType;

    public MessageClient(Channel channel, BasicMember member, MessageClientType clientType) {
        super(channel);
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
