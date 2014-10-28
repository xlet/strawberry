package cn.w.im.plugins.connected;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.MessageType;
import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.messages.client.ConnectResponseMessage;
import cn.w.im.domains.messages.client.FriendGroupMessage;
import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.domains.messages.client.OfflineMessage;
import cn.w.im.domains.messages.server.ConnectedResponseMessage;
import cn.w.im.domains.relation.FriendGroup;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.core.plugins.MessagePlugin;
import cn.w.im.core.server.MessageServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午9:12.
 * Summary: the message server who send ConnectedMessage must waiting to received all server send ConnectedResponseMessage then send ConnectResponseMessage to message client.
 */
public class ConnectedResponsePlugin extends MessagePlugin<ConnectedResponseMessage> {

    private final Logger logger;

    /**
     * 构造函数.
     */
    public ConnectedResponsePlugin() {
        super("ConnectedResponsePlugin", "response connect message.");
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.ConnectedResponse)
                && (context.getServer().getServerType() == ServerType.MessageServer);
    }

    @Override
    protected void processMessage(ConnectedResponseMessage message, PluginContext context) throws ClientNotFoundException {
        switch (context.getServer().getServerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
        }
    }

    private void processMessageWithMessageServer(ConnectedResponseMessage message, PluginContext context) {
        MessageServer currentServer = (MessageServer) context.getServer();
        ConnectToken connectToken = currentServer.getToken(message.getToken());
        String connectMemberId = connectToken.getMember().getId();
        try {
            if (message.isSuccess()) {
                currentServer.respondProvider().receivedRespondedMessage(message);
                if (currentServer.respondProvider().allResponded(message.getRespondKey())) {
                    currentServer.connected(message.getToken());

                    //send connectResponse Message to client,client connected success.
                    BasicMember self = connectToken.getMember();
                    ConnectResponseMessage responseMessage = new ConnectResponseMessage(self);
                    currentServer.messageProvider().send(connectMemberId, responseMessage);

                    //send contracts.
                    Collection<FriendGroup> friendGroups = currentServer.ContactProvider().getFriendGroup(self);
                    for (FriendGroup friendGroup : friendGroups) {
                        FriendGroupMessage friendGroupMessage = new FriendGroupMessage(friendGroup);
                        currentServer.messageProvider().send(connectMemberId, friendGroupMessage);
                    }


                    //send nearlyLinkman message to client.
//                    List<BasicMember> nearlyLinkmen = currentServer.ContactProvider().getRecentContact(self);
//                    currentServer.statusProvider().render(nearlyLinkmen);
//                    RecentContactsMessage nearlyLinkmanMessage = new RecentContactsMessage(nearlyLinkmen);
//                    currentServer.messageProvider().send(connectToken.getOrgCode(), nearlyLinkmanMessage);

                    //send offline message to client.
                    List<NormalMessage> offlineMessages = currentServer.messageProvider().getOfflineMessages(connectMemberId);
                    OfflineMessage offlineMessage = new OfflineMessage(offlineMessages);
                    currentServer.messageProvider().send(connectMemberId, offlineMessage);

                    //将离线消息状态标记为已送达
                    currentServer.messageProvider().setMessageForwarded(connectMemberId);
                }
            } else {
                logger.error("core[" + message.getFromServer().getNodeId() + "] perhaps error! errorCode[" + message.getErrorCode() + "] errorMessage:" + message.getErrorMessage());
                currentServer.respondProvider().interrupt(message.getRespondKey());
            }
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
            ConnectResponseMessage errorResponseMessage = new ConnectResponseMessage(ex.getErrorCode(), ex.getMessage());
            currentServer.messageProvider().send(connectMemberId, errorResponseMessage);
        }
    }
}
