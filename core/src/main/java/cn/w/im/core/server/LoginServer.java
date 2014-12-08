package cn.w.im.core.server;


import cn.w.im.core.*;
import cn.w.im.core.allocate.ConnectToken;
import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.exception.*;
import cn.w.im.core.message.client.ConnectResponseMessage;
import cn.w.im.core.allocate.provider.DefaultMessageServerAllocateProvider;
import cn.w.im.core.allocate.provider.LoggedInException;
import cn.w.im.core.allocate.provider.MessageServerAllocateProvider;
import cn.w.im.core.member.provider.DefaultMemberInfoProviderImpl;
import cn.w.im.core.member.provider.IdPasswordException;
import cn.w.im.core.member.provider.MemberInfoProvider;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.message.Message;
import cn.w.im.core.message.client.LoginMessage;
import cn.w.im.core.message.client.LoginResponseMessage;
import cn.w.im.core.message.server.*;
import cn.w.im.core.util.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午3:16.
 * Summary: 登录服务信息.
 */
public class LoginServer extends ScalableServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServer.class);

    private MessageServerAllocateProvider messageServerAllocateProvider;
    private MemberInfoProvider memberProvider;

    /**
     * 构造函数.
     *
     * @param outerHost outer host.
     * @param port      port.
     */
    public LoginServer(String outerHost, int port) throws UnknownHostException {
        super(ServerType.LoginServer, outerHost, port);
    }

    @Override
    public void start() throws ServerInnerException {
        super.start();
        this.messageServerAllocateProvider = new DefaultMessageServerAllocateProvider();
        this.memberProvider = new DefaultMemberInfoProviderImpl();
    }


    @Override
    public void handlerMessage(MessageHandlerContext context) throws ServerInnerException {

        Message message = context.getMessage();
        switch (message.getMessageType()) {
            case Ready:
                ReadyMessage readyMessage = (ReadyMessage) message;
                this.messageServerReady(context.getChannel(), readyMessage);
                break;
            case Login:
                LoginMessage loginMessage = (LoginMessage) message;
                this.login(loginMessage, context.getChannel(), loginMessage.getClientType());
                break;
            case TokenResponse:
                TokenResponseMessage tokenResponseMessage = (TokenResponseMessage) message;
                this.loginSuccess(tokenResponseMessage);
                break;
            case Connected:
                ConnectedMessage connectedMessage = (ConnectedMessage) message;
                this.connected(connectedMessage);
                break;
            case MemberLogout:
                MemberLogoutMessage memberLogoutMessage = (MemberLogoutMessage) message;
                this.memberLogout(memberLogoutMessage);
                break;
            default:
                super.handlerMessage(context);
                break;
        }
    }

    private void memberLogout(MemberLogoutMessage message) {
        try {
            String memberId = message.getMemberId();
            String clientHost = message.getClientHost();
            MessageClientType clientType = message.getClientType();
            ServerBasic serverBasic = message.getServerBasic();
            this.allocateProvider().disconnected(memberId, clientType, clientHost, serverBasic);
        } catch (ServerInnerException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void connected(ConnectedMessage message) {
        try {
            this.allocateProvider().connected(message.getToken(), message.getMemberId(), message.getClientType(),
                    message.getClientHost(), message.getFromServer());

            ConnectedResponseMessage connectedResponseMessage = new ConnectedResponseMessage(message.getMemberId(), message.getClientHost(),
                    message.getClientType(), message.getToken(),
                    this.getServerBasic(), message.getRespondKey());
            this.messageProvider().send(message.getFromServer(), connectedResponseMessage);
        } catch (ServerInnerException e) {
            LOGGER.error(e.getMessage(), e);
            ConnectResponseMessage responseMessage = new ConnectResponseMessage(e.getErrorCode(), e.getMessage());
            this.messageProvider().send(message.getFromServer(), responseMessage);
        }
    }

    private void loginSuccess(TokenResponseMessage message) {
        if (message.isSuccess()) {
            ConnectToken connectToken = message.getToken();
            String token = connectToken.getToken();
            String memberId = connectToken.getMember().getId();
            String messageHost = this.getMessageHost(connectToken.getClientHost(), connectToken.getAllocatedMessageServer());
            int messagePort = connectToken.getAllocatedMessageServer().getPort();
            LoginResponseMessage loginResponseMessage = new LoginResponseMessage(token, memberId, messageHost, messagePort);
            this.messageProvider().send(connectToken.getMember(), loginResponseMessage);
        } else {
            LOGGER.error("core[" + message.getFromServer().getNodeId() + "] perhaps error! errorCode[" + message.getErrorCode() + "] errorMessage:" + message.getErrorMessage());
        }
    }

    private String getMessageHost(String clientHost, ServerBasic allocatedMessageServer) {

        String messageServerLanHost = allocatedMessageServer.getLanHost();
        String messageServerOuterHost = allocatedMessageServer.getOuterHost();
        if (IpUtils.isInner(clientHost)) {
            return messageServerLanHost;
        }
        return messageServerOuterHost;
    }

    private void login(LoginMessage message, Channel channel, MessageClientType clientType) {
        try {
            String loginId = message.getLoginId();
            String password = message.getPassword();

            //verify login id and password correct.
            BasicMember member = this.memberProvider().verify(loginId, password, message.getProductType());

            //re-register this client and mark this client and member relation.
            this.clientProvider().registerClient(channel.host(), channel.port(), member, clientType);

            //try allocate a message server to client to connect.
            ConnectToken token = this.allocateProvider().allocate(member, clientType, channel.host());

            //send this token to message who allocated
            TokenMessage tokenMessage = new TokenMessage(token, this.getServerBasic());
            this.messageProvider().send(token.getAllocatedMessageServer(), tokenMessage);

        } catch (IdPasswordException idPasswordException) {

            LoginResponseMessage idPasswordErrorMessage = new LoginResponseMessage(idPasswordException.getErrorCode(), idPasswordException.getMessage());
            this.messageProvider().send(channel, idPasswordErrorMessage);

        } catch (LoggedInException loggedInException) {

            LoginResponseMessage loggedInErrorMessage = new LoginResponseMessage(loggedInException.getErrorCode(), loggedInException.getMessage(), loggedInException.getLocalizedMessage());
            this.messageProvider().send(channel, loggedInErrorMessage);

        } catch (ServerInnerException e) {
            LoginResponseMessage loginErrorMessage = new LoginResponseMessage(e.getErrorCode(), e.getMessage());
            this.messageProvider().send(channel, loginErrorMessage);
        }
    }


    private void messageServerReady(Channel channel, ReadyMessage readyMessage) {
        ServerBasic readyMessageServer = readyMessage.getMessageServer();
        this.allocateProvider().messageServerReady(readyMessageServer);

        try {
            this.clientProvider().registerClient(channel.host(), channel.port(), readyMessageServer);
        } catch (ServerInnerException e) {
            LOGGER.error("register ready message server[node:{}] error!", readyMessageServer.getNodeId());
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    protected void registeredAfter(MessageHandlerContext context) {
        //todo:jackie if login message has multi,must sync login allocation status from other login server.
    }

    /**
     * get message server allocate provider.
     *
     * @return allocate provider.
     */
    public MessageServerAllocateProvider allocateProvider() {
        return messageServerAllocateProvider;
    }

    public MemberInfoProvider memberProvider() {
        return this.memberProvider;
    }


}
