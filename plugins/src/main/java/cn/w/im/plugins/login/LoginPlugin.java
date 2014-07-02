package cn.w.im.plugins.login;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.messages.client.LoginMessage;
import cn.w.im.domains.messages.client.LoginResponseMessage;
import cn.w.im.domains.messages.server.TokenMessage;
import cn.w.im.exceptions.*;
import cn.w.im.plugins.MessagePlugin;
import cn.w.im.server.LoginServer;
import cn.w.im.utils.sdk.usercenter.MemberService;
import cn.w.im.utils.sdk.usercenter.Members;
import cn.w.im.utils.sdk.usercenter.UserCenterException;
import cn.w.im.utils.sdk.usercenter.model.Account;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-30 下午5:17.
 * Summary: check message client login info.
 * if login success:
 * save this message client context.
 * send TokenMessage to matched message server and wait for the message server send TokenResponseMessage.
 * if login error:
 * send LoginResponseMessage to message client,the LoginResponseMessage contains the error code.
 */
public class LoginPlugin extends MessagePlugin<LoginMessage> {

    private Log logger = LogFactory.getLog(this.getClass());

    private Members members = new MemberService();

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public LoginPlugin(ServerType containerType) {
        super("LoginPlugin", "login process.", containerType);
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return context.getMessage() instanceof LoginMessage;
    }

    @Override
    public void processMessage(LoginMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (this.containerType()) {
            case LoginServer:
                processWithLoginServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processWithLoginServer(LoginMessage message, PluginContext context) {
        try {
            LoginServer.current().clientCacheProvider().registerClient(message.getClientType(), message.getLoginId(), context.getCurrentHost(), context.getCurrentPort());
            login(message);
            ConnectToken token = LoginServer.current().allocateProvider().allocate(message.getLoginId(), context.getCurrentHost());

            //通知消息服务登陆token信息.
            TokenMessage tokenMessage = new TokenMessage(token, LoginServer.current().getServerBasic());
            LoginServer.current().sendMessageProvider().send(token.getAllocatedMessageServer(), tokenMessage);

            //notify other started login server that this has allocated a message server to login client.
            LoginServer.current().sendMessageProvider().send(ServerType.LoginServer, tokenMessage);
        } catch (IdPasswordException idPasswordException) {
            LoginResponseMessage idPasswordErrorMessage = new LoginResponseMessage(idPasswordException.getErrorCode(), idPasswordException.getMessage());
            LoginServer.current().sendMessageProvider().send(message.getLoginId(), idPasswordErrorMessage);
        } catch (LoggedInException loggedInException) {
            LoginResponseMessage loggedInErrorMessage = new LoginResponseMessage(loggedInException.getErrorCode(), loggedInException.getMessage(), loggedInException.getLocalizedMessage());
            LoginServer.current().sendMessageProvider().send(message.getLoginId(), loggedInErrorMessage);
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private void login(LoginMessage message) throws IdPasswordException, LoggedInException {
        String loginId = message.getLoginId();
        String password = message.getPassword();
        try {
            //this type of client already exists
            Client client = LoginServer.current().clientCacheProvider().getClient(message.getClientType(), loginId);
            throw new LoggedInException(client.getRemoteHost());
        } catch (ClientNotFoundException ex) {
            logger.error(ex.getMessage(), ex);
        }
        try {
            if(!members.verify(new Account(loginId, password))){
                throw new IdPasswordException(loginId);
            }
        } catch (UserCenterException e) {
            logger.error(e.getMessage());
        }
    }
}
