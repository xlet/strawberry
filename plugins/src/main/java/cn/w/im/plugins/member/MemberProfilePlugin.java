package cn.w.im.plugins.member;

import cn.w.im.domains.MessageType;
import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.basic.Member;
import cn.w.im.domains.messages.client.GetProfileRequestMessage;
import cn.w.im.domains.messages.client.GetProfileResponseMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.core.plugins.MessagePlugin;
import cn.w.im.core.server.MessageServer;

import java.util.List;

/**
 * Creator: JimmyLin
 * DateTime: 14-7-22 上午11:36
 * Summary:
 */
public class MemberProfilePlugin extends MessagePlugin<GetProfileRequestMessage> {

    public MemberProfilePlugin() {
        super("ProfilePlugin", "for profile request.");
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType().equals(MessageType.GetProfileRequest))
                && (context.getServer().getServerType() == ServerType.MessageServer);
    }

    @Override
    protected void processMessage(GetProfileRequestMessage message, PluginContext context) throws ClientNotFoundException {
        MessageServer currentServer = (MessageServer) context.getServer();
        List<Member> members = currentServer.linkmanProvider().getMembers(message.getIds());
        currentServer.messageProvider().send(context.getCurrentHost(), context.getCurrentPort(), new GetProfileResponseMessage(members));
    }
}
