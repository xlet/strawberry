package cn.w.im.plugins.login;

import cn.w.im.domains.ConnectToken;
import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.messages.client.LoginMessage;
import cn.w.im.domains.messages.client.LoginResponseMessage;
import cn.w.im.domains.messages.server.TokenMessage;
import cn.w.im.exceptions.*;
import cn.w.im.core.plugins.MessagePlugin;
import cn.w.im.core.server.LoginServer;
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
            BasicMember member = login(message, context, currentServer);

            currentServer.clientCacheProvider().registerClient(message.getProductType(), message.getClientType(),
                    member.getId(), context.getCurrentHost(), context.getCurrentPort());

            ConnectToken token = currentServer.allocateProvider().allocate(member, context.getCurrentHost());
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
        } catch (ServerInnerException e) {
            LoginResponseMessage loginErrorMessage = new LoginResponseMessage(e.getErrorCode(), e.getMessage());
            currentServer.messageProvider().send(context.getCurrentHost(), context.getCurrentPort(), loginErrorMessage);
        }

    }

    private BasicMember login(LoginMessage message, PluginContext context, LoginServer currentServer) throws IdPasswordException, LoggedInException {
        String loginId = message.getLoginId();
        String password = message.getPassword();

        BasicMember member = currentServer.memberProvider().verify(loginId, password, message.getProductType());
        // login but not connected message server.
        if ((currentServer.allocateProvider().isAllocated(member.getId(), context.getCurrentHost()))
                && (currentServer.allocateProvider().isConnected(member.getId(), context.getCurrentHost()))) {
            logger.debug(loginId + "has login");
            throw new LoggedInException(context.getCurrentHost());
        }
        return member;
    }
}
