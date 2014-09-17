package cn.w.im.plugins.login;

import cn.w.im.domains.ConnectToken;
import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.messages.client.LoginMessage;
import cn.w.im.domains.messages.client.LoginResponseMessage;
import cn.w.im.domains.messages.server.TokenMessage;
import cn.w.im.exceptions.*;
import cn.w.im.core.plugins.MessagePlugin;
import cn.w.im.core.server.LoginServer;
import cn.w.im.utils.sdk.usercenter.Members;
import cn.w.im.utils.sdk.usercenter.UserCenterException;
import cn.w.im.utils.sdk.usercenter.model.Account;
import cn.w.im.utils.spring.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Members members = SpringContext.context().getBean("members", Members.class);

    /**
     * 构造函数.
     */
    public LoginPlugin() {
        super("LoginPlugin", "login process.");
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.Login)
                && (context.getServer().getServerType() == ServerType.LoginServer);
    }

    @Override
    public void processMessage(LoginMessage message, PluginContext context) throws ClientNotFoundException {
        switch (context.getServer().getServerType()) {
            case LoginServer:
                processWithLoginServer(message, context);
                break;
        }
    }

    private void processWithLoginServer(LoginMessage message, PluginContext context) {
        LoginServer currentServer = (LoginServer) context.getServer();
        try {
            login(message, context, currentServer);

            ConnectToken token = currentServer.allocateProvider().allocate(message.getLoginId(), context.getCurrentHost());
            //通知消息服务登陆token信息.
            TokenMessage tokenMessage = new TokenMessage(token, currentServer.getServerBasic());
            currentServer.messageProvider().send(token.getAllocatedMessageServer(), tokenMessage);

            //notify other started login server that this has allocated a message server to login client.
            currentServer.messageProvider().send(ServerType.LoginServer, tokenMessage);
        } catch (IdPasswordException idPasswordException) {
            LoginResponseMessage idPasswordErrorMessage = new LoginResponseMessage(idPasswordException.getErrorCode(), idPasswordException.getMessage());
            currentServer.messageProvider().send(context.getCurrentHost(), context.getCurrentPort(), idPasswordErrorMessage);
        } catch (LoggedInException loggedInException) {
            LoginResponseMessage loggedInErrorMessage = new LoginResponseMessage(loggedInException.getErrorCode(), loggedInException.getMessage(), loggedInException.getLocalizedMessage());
            currentServer.messageProvider().send(context.getCurrentHost(), context.getCurrentPort(), loggedInErrorMessage);
        }

    }

    private void login(LoginMessage message, PluginContext context,LoginServer currentServer) throws IdPasswordException, LoggedInException {
        String loginId = message.getLoginId();
        String password = message.getPassword();

        try {
            currentServer.clientCacheProvider().getClient(message.getClientType(), message.getLoginId());
            //if the specified client has connected to message server
            if (currentServer.allocateProvider().isConnected(message.getClientType(), loginId, context.getCurrentHost())) {
                logger.debug(loginId + " has login ");
                throw new LoggedInException(context.getCurrentHost());
            }
        } catch (ClientNotFoundException e) {
            logger.error(e.getMessage());
        }

        try {
            boolean loginSuccess = members.verify(new Account(loginId, password));
            if (loginSuccess) {
                // try to register
                currentServer.clientCacheProvider().registerClient(message.getClientType(), message.getLoginId(), context.getCurrentHost(), context.getCurrentPort());
            } else {
                throw new IdPasswordException(loginId);
            }

        } catch (UserCenterException e) {
            logger.error(e.getMessage());
            throw new IdPasswordException(loginId);
        } catch (ClientNotRegisterException e) {
            logger.debug(e.getMessage());
        } catch (MessageClientRegisteredException e) {
            logger.debug(e.getMessage());
        }
    }
}
