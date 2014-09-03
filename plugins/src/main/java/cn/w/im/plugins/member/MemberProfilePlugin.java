package cn.w.im.plugins.member;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.basic.Member;
import cn.w.im.domains.messages.client.GetProfileRequestMessage;
import cn.w.im.domains.messages.client.GetProfileResponseMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;
import cn.w.im.core.server.MessageServer;

import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-22 上午11:36
 * Summary:
 */
public class MemberProfilePlugin extends MessagePlugin<GetProfileRequestMessage> {

    public MemberProfilePlugin(ServerType containerType) {
        super("ProfilePlugin", "for profile request.", containerType);
    }

    @Override
    protected boolean isMatch(PluginContext context) {
        return context.getMessage().getMessageType().equals(MessageType.GetProfileRequest);
    }

    @Override
    protected void processMessage(GetProfileRequestMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        MessageServer messageServer = MessageServer.current();
        List<Member> members = messageServer.linkmanProvider().getMembers(message.getIds());
        messageServer.messageProvider().send(context.getCurrentHost(), context.getCurrentPort(), new GetProfileResponseMessage(members));
    }
}
