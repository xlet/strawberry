package org.xlet.strawberry.core.status.memberAll;

import org.xlet.strawberry.core.MessageHandlerContext;
import org.xlet.strawberry.core.client.provider.ClientProvider;
import org.xlet.strawberry.core.member.provider.DefaultMemberInfoProviderImpl;
import org.xlet.strawberry.core.member.provider.MemberInfoProvider;
import org.xlet.strawberry.core.message.client.*;
import org.xlet.strawberry.core.message.provider.MessageProvider;
import org.xlet.strawberry.core.member.relation.ContactProvider;
import org.xlet.strawberry.core.member.relation.DefaultContactProviderImpl;
import org.xlet.strawberry.core.allocate.ConnectToken;
import org.xlet.strawberry.core.exception.ErrorCodeDefine;
import org.xlet.strawberry.core.status.basicStatus.DefaultStatusProvider;
import org.xlet.strawberry.core.status.basicStatus.StatusProvider;
import org.xlet.strawberry.core.status.recentContact.DefaultRecentContactProviderImpl;
import org.xlet.strawberry.core.status.recentContact.RecentContactProvider;
import org.xlet.strawberry.core.server.ServerType;
import org.xlet.strawberry.core.status.basicStatus.Status;
import org.xlet.strawberry.core.client.MessageClientType;
import org.xlet.strawberry.core.member.BasicMember;
import org.xlet.strawberry.core.message.server.ConnectedResponseMessage;
import org.xlet.strawberry.core.message.server.TokenMessage;
import org.xlet.strawberry.core.message.server.TokenResponseMessage;
import org.xlet.strawberry.core.exception.ServerInnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * default member status provider implement.
 */
public class DefaultMemberAllProviderImpl implements MemberAllProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMemberAllProviderImpl.class);

    private StatusProvider statusProvider;
    private ContactProvider contactProvider;
    private RecentContactProvider recentContactProvider;
    private MemberInfoProvider memberInfoProvider;
    private MessageProvider messageProvider;
    private ClientProvider clientProvider;

    private Map<String, MemberAll> memberMap;

    public DefaultMemberAllProviderImpl(MessageProvider messageProvider, ClientProvider clientProvider) throws ServerInnerException {
        this.memberInfoProvider = new DefaultMemberInfoProviderImpl();
        this.statusProvider = new DefaultStatusProvider();
        this.contactProvider = new DefaultContactProviderImpl(this.memberInfoProvider);
        this.recentContactProvider = new DefaultRecentContactProviderImpl();
        this.memberMap = new ConcurrentHashMap<String, MemberAll>();
        this.messageProvider = messageProvider;
        this.clientProvider = clientProvider;
        this.clientProvider.registerClientRemoveListener(new MessageClientRemoveListener(this));
    }

    public MemberAll getMember(BasicMember member) throws MemberAllNotExisted {
        return this.getMember(member.getId());
    }

    private void connected(MessageHandlerContext context) {
        ConnectedResponseMessage message = (ConnectedResponseMessage) context.getMessage();
        try {
            if (message.isSuccess()) {
                String memberId = message.getMemberId();
                MemberAll memberAll = this.getMember(memberId);
                memberAll.connected(message.getClientType());
            } else {
                LOGGER.error("core[" + message.getFromServer().getNodeId() + "] perhaps error! errorCode[" + message.getErrorCode() + "] errorMessage:" + message.getErrorMessage());
                ConnectResponseMessage responseMessage = new ConnectResponseMessage(ErrorCodeDefine.SERVER_INNER_ERROR_CODE, "server is error!");
                this.messageProvider.send(context.getChannel(), responseMessage);
            }
        } catch (MemberAllNotExisted ex) {
            String errorMessage = "perhaps send error member id,server not this member id token info.";
            ConnectResponseMessage responseMessage = new ConnectResponseMessage(ex.getErrorCode(), errorMessage);
            this.messageProvider.send(context.getChannel(), responseMessage);
        } catch (ServerInnerException e) {
            ConnectResponseMessage responseMessage = new ConnectResponseMessage(e.getErrorCode(), e.getMessage());
            this.messageProvider.send(context.getChannel(), responseMessage);
        }
    }

    private MemberAll getMemberAndCreateIfNotExisted(BasicMember member) throws ServerInnerException {
        if (this.memberMap.containsKey(member.getId())) {
            return this.memberMap.get(member.getId());
        }
        MemberAll memberAll = new MemberAll(member, this.memberInfoProvider, this.statusProvider,
                this.contactProvider, this.recentContactProvider, this.messageProvider, this.clientProvider);
        this.memberMap.put(member.getId(), memberAll);
        return memberAll;
    }

    private MemberAll getMember(String memberId) throws MemberAllNotExisted {

        if (this.memberMap.containsKey(memberId)) {
            return this.memberMap.get(memberId);
        }
        throw new MemberAllNotExisted(memberId);
    }

    private void tokenReceived(MessageHandlerContext context) throws ServerInnerException {
        TokenMessage tokenMessage = (TokenMessage) context.getMessage();
        ConnectToken connectToken = tokenMessage.getToken();
        BasicMember member = connectToken.getMember();
        MemberAll memberAll = this.getMemberAndCreateIfNotExisted(member);
        memberAll.addToken(connectToken);

        //todo:jackie this response is any necessary.
        TokenResponseMessage responseMessage = new TokenResponseMessage(connectToken,
                context.getServer().getServerBasic(), tokenMessage.getRespondKey());
        this.messageProvider.send(ServerType.LoginServer, responseMessage);
    }

    private void connect(MessageHandlerContext context) {
        ConnectMessage message = (ConnectMessage) context.getMessage();
        String memberId = message.getMemberId();
        MessageClientType clientType = message.getClientType();
        String token = message.getToken();
        try {
            MemberAll memberAll = this.getMember(memberId);
            memberAll.connect(token, context.getChannel(), clientType, context.getServer().getServerBasic());

        } catch (MemberAllNotExisted ex) {
            String errorMessage = "perhaps send error member id,server not this member id token info.";
            ConnectResponseMessage responseMessage = new ConnectResponseMessage(ex.getErrorCode(), errorMessage);
            this.messageProvider.send(context.getHost(), context.getPort(), responseMessage);
        }
    }

    private void sendMessage(MessageHandlerContext context) {
        try {
            NormalMessage message = (NormalMessage) context.getMessage();
            String memberId = message.getFrom();
            MemberAll memberAll = this.getMember(memberId);
            memberAll.sendMessage(message);
        } catch (MemberAllNotExisted ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private void logout(MessageHandlerContext context) {
        try {
            LogoutMessage message = (LogoutMessage) context.getMessage();
            String memberId = message.getMemberId();
            MessageClientType clientType = message.getClientType();
            MemberAll memberAll = this.getMember(memberId);
            memberAll.logout(clientType);
        } catch (ServerInnerException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

    }

    @Override
    public void handlerMessage(MessageHandlerContext context) throws ServerInnerException {
        switch (context.getMessage().getMessageType()) {
            case Token:
                this.tokenReceived(context);
                break;
            case Connect:
                this.connect(context);
                break;
            case ConnectedResponse:
                this.connected(context);
                break;
            case Normal:
                this.sendMessage(context);
                break;
            case Status:
                this.memberStatusChange(context);
                break;
            case Logout:
                this.logout(context);
                break;
        }
    }

    private void memberStatusChange(MessageHandlerContext context) {
        try {
            MemberStatusMessage message = (MemberStatusMessage) context.getMessage();
            String memberId = message.getMemberId();
            Status memberStatus = Status.valueOf(message.getStatus());
            MemberAll memberAll = this.getMember(memberId);
            memberAll.statusChange(memberStatus);
        } catch (ServerInnerException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


}
