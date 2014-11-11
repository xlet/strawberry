package cn.w.im.core.server;


import cn.w.im.core.*;
import cn.w.im.core.providers.allocate.DefaultMessageServerAllocateProvider;
import cn.w.im.core.providers.allocate.MessageServerAllocateProvider;
import cn.w.im.core.providers.member.DefaultMemberInfoProviderImpl;
import cn.w.im.core.providers.member.MemberInfoProvider;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.message.Message;
import cn.w.im.core.message.client.LoginMessage;
import cn.w.im.core.message.client.LoginResponseMessage;
import cn.w.im.core.message.server.*;
import cn.w.im.core.exception.IdPasswordException;
import cn.w.im.core.exception.LoggedInException;
import cn.w.im.core.exception.ServerInnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * @param port port.
     */
    public LoginServer(int port) {
        super(ServerType.LoginServer, port);
    }

    @Override
    public void start() {
        super.start();
        this.messageServerAllocateProvider = new DefaultMessageServerAllocateProvider();
        this.memberProvider = new DefaultMemberInfoProviderImpl();
    }


    @Override
    public void handlerMessage(MessageHandlerContext context) {
        super.handlerMessage(context);
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
            case Token:
                TokenMessage tokenMessage = (TokenMessage) message;
                this.allocateProvider().syncAllocation(tokenMessage.getToken());
                break;
            case TokenResponse:
                TokenResponseMessage tokenResponseMessage = (TokenResponseMessage) message;
                this.loginSuccess(tokenResponseMessage);
                break;
            case Connected:
                ConnectedMessage connectedMessage = (ConnectedMessage) message;
                this.connected(connectedMessage);
                break;
            default:
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("message[{}] is ignore!", message.getMessageType());
                }
                break;
        }
    }

    private void connected(ConnectedMessage message) {
        this.allocateProvider().connected(message.getToken(), message.getMemberId(),
                message.getClientHost(), message.getClientType(), message.getFromServer());

        ConnectedResponseMessage connectedResponseMessage = new ConnectedResponseMessage(message.getMemberId(), message.getClientHost(),
                message.getClientType(), message.getToken(),
                this.getServerBasic(), message.getRespondKey());
        this.messageProvider().send(message.getFromServer(), connectedResponseMessage);
    }

    private void loginSuccess(TokenResponseMessage message) {
        if (message.isSuccess()) {
            ConnectToken token = message.getToken();
            LoginResponseMessage loginResponseMessage = new LoginResponseMessage(token);
            this.messageProvider().send(token.getMember(), loginResponseMessage);
        } else {
            LOGGER.error("core[" + message.getFromServer().getNodeId() + "] perhaps error! errorCode[" + message.getErrorCode() + "] errorMessage:" + message.getErrorMessage());
        }
    }

    private void login(LoginMessage message, Channel channel, MessageClientType clientType) {
        try {
            String loginId = message.getLoginId();
            String password = message.getPassword();

            //verify login id and password correct.
            BasicMember member = this.memberProvider().verify(loginId, password, message.getProductType());

            //re-register this client and mark this client and member relation.
            this.clientProvider().registerClient(channel, member, clientType);

            //try allocate a message server to client to connect.
            ConnectToken token = this.allocateProvider().allocate(member, channel.currentHost(), clientType);

            //send this token to message who allocated
            TokenMessage tokenMessage = new TokenMessage(token, this.getServerBasic());
            this.messageProvider().send(token.getAllocatedMessageServer(), tokenMessage);

            //notify other started login server that this has allocated a message server to login client.
            this.messageProvider().send(ServerType.LoginServer, tokenMessage);

            //todo:jackie waite the allocated message server and other login server response token received has any necessary!!!

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
        try {
            ServerBasic readyMessageServer = readyMessage.getMessageServer();
            //todo:jackie has duplicate registered.
            this.clientProvider().registerClient(channel, readyMessageServer);
            this.allocateProvider().messageServerReady(readyMessageServer);
        } catch (ServerInnerException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    @Override
    protected void registeredAfter(MessageHandlerContext context) {
        //todo:jackie register to message server and request message server linked clients count.
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
