package cn.w.im.plugins.login;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.PluginContext;
import cn.w.im.domains.messages.server.TokenResponseMessage;
import cn.w.im.domains.messages.client.LoginResponseMessage;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;
import cn.w.im.server.LoginServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-21 下午1:54.
 * Summary: message server send TokenResponseMessage means the message server ready to connect.
 * login server receive TokenResponseMessage then send LoginResponseMessage to messageClient and close connection.
 */
public class TokenResponsePlugin extends MessagePlugin<TokenResponseMessage> {

    private Log logger;

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public TokenResponsePlugin(ServerType containerType) {
        super("TokenResponsePlugin", "send loginResponse message to client.", containerType);
        this.logger = LogFactory.getLog(this.getClass());
    }

    @Override
    protected boolean isMatch(PluginContext context) {
        return context.getMessage().getMessageType().equals(MessageType.TokenResponse);
    }

    @Override
    protected void processMessage(TokenResponseMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (this.containerType()) {
            case LoginServer:
                processMessageWithLoginServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithLoginServer(TokenResponseMessage message, PluginContext context) {
        if (message.isSuccess()) {
            ConnectToken token = message.getToken();
            LoginResponseMessage loginResponseMessage = new LoginResponseMessage(token);
            LoginServer.current().sendMessageProvider().send(token.getLoginId(), loginResponseMessage);
        } else {
            logger.error("server[" + message.getFromServer().getNodeId() + "] perhaps error! errorCode[" + message.getErrorCode() + "] errorMessage:" + message.getErrorMessage());
        }
    }
}
