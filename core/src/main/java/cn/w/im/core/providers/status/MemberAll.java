package cn.w.im.core.providers.status;

import cn.w.im.core.providers.member.MemberInfoProvider;
import cn.w.im.core.providers.message.MessageProvider;
import cn.w.im.core.providers.relation.ContactProvider;
import cn.w.im.core.ConnectToken;
import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.ServerType;
import cn.w.im.core.Status;
import cn.w.im.core.MessageClientType;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.message.client.*;
import cn.w.im.core.message.server.ConnectedMessage;
import cn.w.im.core.member.relation.FriendGroup;
import cn.w.im.core.member.relation.RecentContactStatuses;
import cn.w.im.core.member.MemberStatus;
import cn.w.im.core.exception.ContactNotExistedException;
import cn.w.im.core.exception.TokenErrorException;
import cn.w.im.core.exception.TokenNotExistedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * member all in this.
 */
public class MemberAll {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberAll.class);

    private StatusProvider statusProvider;
    private ContactProvider contactProvider;
    private RecentContactProvider recentContactProvider;
    private MemberInfoProvider memberInfoProvider;
    private MessageProvider messageProvider;

    private Map<MessageClientType, ConnectToken> connectTokenMap;

    private BasicMember self;

    public MemberAll(BasicMember self,
                     MemberInfoProvider memberInfoProvider,
                     StatusProvider statusProvider,
                     ContactProvider contactProvider,
                     RecentContactProvider recentContactProvider,
                     MessageProvider messageProvider) {
        this.memberInfoProvider = memberInfoProvider;
        this.statusProvider = statusProvider;
        this.contactProvider = contactProvider;
        this.recentContactProvider = recentContactProvider;
        this.messageProvider = messageProvider;
        this.self = self;
        this.memberInfoProvider.addMember(self);
        this.connectTokenMap = new ConcurrentHashMap<MessageClientType, ConnectToken>();
    }

    public BasicMember self() {
        return this.self;
    }

    public MemberStatus status() {
        return this.statusProvider.status(this.self);
    }

    public Collection<FriendGroup> friendGroups() {
        return this.contactProvider.getFriendGroup(this.self);
    }

    public RecentContactStatuses recentContacts() {
        return this.recentContactProvider.get(this.self);
    }

    public void connected(MessageClientType clientType) {

        this.removeToken(clientType);

        this.online();

        //send connectResponse Message to client,client connected success.
        ConnectResponseMessage responseMessage = new ConnectResponseMessage(this.self);
        this.messageProvider.send(this.self, responseMessage);

        this.sendContactAndStatus();
        this.sendRecentContactStatus();
        this.sendOfflineMessageAddSetForward();
    }

    private void removeToken(MessageClientType clientType) {
        if (this.connectTokenMap.containsKey(clientType)) {
            this.connectTokenMap.remove(clientType);
        }
    }


    /**
     * this  member status changed.
     * <p/>
     * tell all contacts who is online that status changed.
     *
     * @param status status.
     */
    private void statusChange(Status status) {
        this.statusProvider.change(this.self, status);
        MemberStatusMessage memberStatusMessage = new MemberStatusMessage(this.self.getId(), status.getValue());
        for (FriendGroup friendGroup : this.friendGroups()) {
            for (BasicMember contact : friendGroup.getContacts()) {
                if (this.statusProvider.online(contact)) {
                    this.messageProvider.send(contact, memberStatusMessage);
                }
            }
        }
    }

    /**
     * set this member online.
     */
    private void online() {
        this.statusChange(Status.Online);
    }

    /**
     * send contracts and contracts status.
     */
    private void sendContactAndStatus() {
        Collection<FriendGroup> friendGroups = this.friendGroups();
        if (friendGroups.size() != 0) {
            for (FriendGroup friendGroup : friendGroups) {
                FriendGroupMessage friendGroupMessage = new FriendGroupMessage(friendGroup);
                this.messageProvider.send(this.self, friendGroupMessage);
                for (BasicMember contact : friendGroup.getContacts()) {
                    if (this.statusProvider.online(contact)) {
                        Status contactStatus = this.statusProvider.status(contact).getStatus();
                        MemberStatusMessage statusMessage = new MemberStatusMessage(contact.getId(), contactStatus.getValue());
                        this.messageProvider.send(this.self, statusMessage);
                    }
                }
            }
        }
    }

    /**
     * send recent contract status.
     */
    private void sendRecentContactStatus() {
        RecentContactStatuses recentContacts = this.recentContacts();
        if (recentContacts.getContactStatuses().size() != 0) {
            RecentContactsMessage recentContactsMessage = new RecentContactsMessage(recentContacts.getContactStatuses());
            this.messageProvider.send(this.self, recentContactsMessage);
        }
    }

    /**
     * send offline message to client,and set these message forwarded.
     */
    private void sendOfflineMessageAddSetForward() {
        Collection<NormalMessage> offlineMessages = this.messageProvider.getOfflineMessages(this.self);
        if (offlineMessages.size() != 0) {
            OfflineMessage offlineMessage = new OfflineMessage(offlineMessages);
            this.messageProvider.send(this.self, offlineMessage);

            //将离线消息状态标记为已送达
            this.messageProvider.setMessageForwarded(this.self);
        }
    }

    public void addToken(ConnectToken connectToken) {
        MessageClientType clientType = connectToken.getClientType();
        if (!this.connectTokenMap.containsKey(clientType)) {

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("register connect token:clientType[{}],clientHost[{}],token[{}]", clientType,
                        connectToken.getClientHost(), connectToken.getToken());
            }

            this.connectTokenMap.put(clientType, connectToken);

        } else {

            if (LOGGER.isDebugEnabled()) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("connect token:clientType[{}],clientHost[{}],token[{}] is existed!", clientType,
                            connectToken.getClientHost(), connectToken.getToken());
                }
            }
            //todo:jackie throws a exception that token existed. perhaps login server occur some error!
            //todo:jackie if login server send connect token to this,this accept and not notify login server this has accepted,then this replace connect token?
        }
    }

    public void connect(String token, String clientHost, int clientPort,
                        MessageClientType clientType, ProductType productType, ServerBasic connectServerBasic) {
        try {
            this.checkToken(token, clientHost, clientType);

            ConnectedMessage connectedMessage = new ConnectedMessage(token, this.self.getId(), clientHost, clientType, connectServerBasic);

            this.messageProvider.send(ServerType.LoginServer, connectedMessage);
        } catch (TokenNotExistedException ex) {
            LOGGER.info(ex.getMessage(), ex);
            ConnectResponseMessage errorResponse = new ConnectResponseMessage(ex.getErrorCode(), ex.getMessage());
            this.messageProvider.send(this.self, errorResponse);
        } catch (TokenErrorException ex) {
            LOGGER.info(ex.getInnerMessage(), ex);
            ConnectResponseMessage errorResponse = new ConnectResponseMessage(ex.getErrorCode(), ex.getMessage());
            this.messageProvider.send(this.self, errorResponse);
        }
    }

    private void checkToken(String token, String clientHost, MessageClientType clientType) throws TokenNotExistedException, TokenErrorException {
        if (!this.connectTokenMap.containsKey(clientType)) {
            throw new TokenNotExistedException(clientType, clientHost, token);
        }
        ConnectToken connectToken = this.connectTokenMap.get(clientType);
        if ((!connectToken.getToken().equals(token)) || (!connectToken.getClientHost().equals(clientHost))) {
            throw new TokenErrorException(connectToken.getToken(), connectToken.getClientHost(), token, clientHost);
        }
    }

    public void sendMessage(NormalMessage message) {
        try {
            String toMemberId = message.getTo();
            BasicMember contact = this.getContact(toMemberId);

            this.recentContactProvider.change(this.self, contact, message.getContent());
            this.messageProvider.send(contact, message);
        } catch (ContactNotExistedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


    private BasicMember getContact(String toMemberId) throws ContactNotExistedException {
        return this.contactProvider.getContact(toMemberId);
    }

    public void logout(MessageClientType messageClientType) {

        this.recentContactProvider.onMemberLogout(this.self);

        LogoutResponseMessage logoutResponseMessage = new LogoutResponseMessage(true);
        //send to login server to notify that member logout.
        this.messageProvider.send(ServerType.LoginServer, logoutResponseMessage);

        //response to client
        this.messageProvider.send(this.self, logoutResponseMessage);

    }
}
