package cn.w.im.core.providers.status;

import cn.w.im.core.*;
import cn.w.im.core.exception.*;
import cn.w.im.core.message.server.MemberLogoutMessage;
import cn.w.im.core.providers.client.Client;
import cn.w.im.core.providers.client.ClientProvider;
import cn.w.im.core.providers.member.MemberInfoProvider;
import cn.w.im.core.providers.message.MessageProvider;
import cn.w.im.core.providers.relation.ContactProvider;
import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.message.client.*;
import cn.w.im.core.message.server.ConnectedMessage;
import cn.w.im.core.member.relation.FriendGroup;
import cn.w.im.core.member.relation.RecentContactStatuses;
import cn.w.im.core.member.MemberStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private ClientProvider clientProvider;

    private Map<MessageClientType, ConnectToken> connectTokenMap;

    private BasicMember self;

    private Collection<MessageClientType> connectedClientTypes;

    public MemberAll(BasicMember self,
                     MemberInfoProvider memberInfoProvider,
                     StatusProvider statusProvider,
                     ContactProvider contactProvider,
                     RecentContactProvider recentContactProvider,
                     MessageProvider messageProvider, ClientProvider clientProvider) {
        this.memberInfoProvider = memberInfoProvider;
        this.statusProvider = statusProvider;
        this.contactProvider = contactProvider;
        this.recentContactProvider = recentContactProvider;
        this.messageProvider = messageProvider;
        this.clientProvider = clientProvider;
        this.self = self;
        this.memberInfoProvider.addMember(self);
        this.connectTokenMap = new ConcurrentHashMap<MessageClientType, ConnectToken>();
        this.connectedClientTypes = new CopyOnWriteArrayList<MessageClientType>();
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

    public Collection<Client> connectedClients() {
        return this.clientProvider.getClient(this.self);
    }

    public void connected(MessageClientType clientType) throws ClientTypeConnectedException {

        this.removeToken(clientType);

        this.markConnected(clientType);

        this.online();

        //send connectResponse Message to client,client connected success.
        ConnectResponseMessage responseMessage = new ConnectResponseMessage(this.self);
        this.messageProvider.send(this.self, responseMessage);

        this.sendContactAndStatus();
        this.sendRecentContactStatus();
        this.sendOfflineMessageAddSetForward();
    }

    private boolean isConnected(MessageClientType clientType) {
        return this.connectedClientTypes.contains(clientType);
    }

    private void markConnected(MessageClientType clientType) throws ClientTypeConnectedException {
        if (!this.connectedClientTypes.contains(clientType)) {
            this.connectedClientTypes.add(clientType);
        } else {
            Client client = this.getConnectedClient(clientType);
            if (client != null) {
                throw new ClientTypeConnectedException(this.self, clientType, client.host(), client.port());
            } else {
                LOGGER.error("status error! member[{}] client[{}] connected,but not active client.", this.self.getId(), clientType);
            }
        }
    }

    private void markDisconnect(MessageClientType clientType) {
        if (this.connectedClientTypes.contains(clientType)) {
            this.connectedClientTypes.remove(clientType);
        }
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
    public void statusChange(Status status) {
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

    public void connect(String token, Channel channel, MessageClientType clientType, ServerBasic connectServerBasic) {

        try {
            //check token correct.
            String clientHost = channel.currentHost();
            this.checkToken(token, clientHost, clientType);

            //re-register to mark memberId and client relation.
            this.clientProvider.registerClient(channel.currentHost(), channel.currentPort(), this.self, clientType);

            //notify login server member connected.
            ConnectedMessage connectedMessage = new ConnectedMessage(token, this.self.getId(), clientHost, clientType, connectServerBasic);
            this.messageProvider.send(ServerType.LoginServer, connectedMessage);
        } catch (TokenNotExistedException e) {
            LOGGER.info(e.getMessage(), e);
            ConnectResponseMessage errorResponse = new ConnectResponseMessage(e.getErrorCode(), e.getMessage());
            this.messageProvider.send(channel, errorResponse);
        } catch (TokenErrorException e) {
            LOGGER.info(e.getInnerMessage(), e);
            ConnectResponseMessage errorResponse = new ConnectResponseMessage(e.getErrorCode(), e.getMessage());
            this.messageProvider.send(channel, errorResponse);
        } catch (ServerInnerException e) {
            LOGGER.error("re-register message client error");
            LOGGER.error(e.getMessage(), e);
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

    public void logout(MessageClientType clientType) {

        Client client = this.getConnectedClient(clientType);
        if (client == null) return;

        //notify recent contact provider member logout.
        this.recentContactProvider.onMemberLogout(this.self);

        //send to login server to notify that member logout.
        ServerBasic connectedServer = client.connectedServer();
        MemberLogoutMessage message = new MemberLogoutMessage(this.self.getId(), client.host(), clientType, connectedServer);
        this.messageProvider.send(ServerType.LoginServer, message);

        //response to client
        LogoutResponseMessage logoutResponseMessage = new LogoutResponseMessage(true);
        this.messageProvider.send(this.self, logoutResponseMessage);

        this.markDisconnect(clientType);
    }

    private Client getConnectedClient(MessageClientType clientType) {
        try {
            return this.clientProvider.getClient(this.self, clientType);
        } catch (ClientNotFoundException e) {
            LOGGER.error("get member[{}] client[{}] error!", this.self.getId(), clientType);
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public boolean isLogout(MessageClientType clientType) {
        return this.isConnected(clientType);
    }
}
