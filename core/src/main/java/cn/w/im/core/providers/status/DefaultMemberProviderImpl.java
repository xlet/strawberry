package cn.w.im.core.providers.status;

import cn.w.im.core.MessageHandlerContext;
import cn.w.im.core.exception.ClientNotRegisterException;
import cn.w.im.core.exception.MessageClientRegisteredException;
import cn.w.im.core.providers.client.Client;
import cn.w.im.core.providers.client.ClientProvider;
import cn.w.im.core.providers.client.ClientRemoveListener;
import cn.w.im.core.providers.member.DefaultMemberInfoProviderImpl;
import cn.w.im.core.providers.member.MemberInfoProvider;
import cn.w.im.core.providers.message.MessageProvider;
import cn.w.im.core.providers.relation.ContactProvider;
import cn.w.im.core.providers.relation.DefaultContactProviderImpl;
import cn.w.im.core.ConnectToken;
import cn.w.im.core.ErrorCodeDefine;
import cn.w.im.core.ServerType;
import cn.w.im.core.Status;
import cn.w.im.core.MessageClientType;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.message.client.*;
import cn.w.im.core.message.server.ConnectedResponseMessage;
import cn.w.im.core.message.server.TokenMessage;
import cn.w.im.core.message.server.TokenResponseMessage;
import cn.w.im.core.exception.MemberAllNotExisted;
import cn.w.im.core.exception.ServerInnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * default member status provider implement.
 */
public class DefaultMemberProviderImpl implements MemberProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultMemberProviderImpl.class);

    private StatusProvider statusProvider;
    private ContactProvider contactProvider;
    private RecentContactProvider recentContactProvider;
    private MemberInfoProvider memberInfoProvider;
    private MessageProvider messageProvider;
    private ClientProvider clientProvider;

    private Map<String, MemberAll> memberMap;

    public DefaultMemberProviderImpl(MessageProvider messageProvider, ClientProvider clientProvider) {
        this.statusProvider = new DefaultStatusProvider();
        this.contactProvider = new DefaultContactProviderImpl();
        this.recentContactProvider = new DefaultRecentContactProviderImpl();
        this.memberInfoProvider = new DefaultMemberInfoProviderImpl();
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
                ConnectResponseMessage responseMessage = new ConnectResponseMessage(ErrorCodeDefine.SERVERINNERERROR, "server is error!");
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

    private MemberAll getMemberAndCreateIfNotExisted(BasicMember member) {
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

    private void tokenReceived(MessageHandlerContext context) {
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
            this.messageProvider.send(context.getCurrentHost(), context.getCurrentPort(), responseMessage);
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
        } catch (MemberAllNotExisted ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

    }

    @Override
    public void handlerMessage(MessageHandlerContext context) {
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
        } catch (MemberAllNotExisted e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


}
