package cn.w.im.plugins.login;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.MessageType;
import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.messages.server.TokenResponseMessage;
import cn.w.im.domains.messages.client.LoginResponseMessage;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.core.plugins.MessagePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-21 下午1:54.
 * Summary: message server send TokenResponseMessage means the message server ready to connect.
 * login server receive TokenResponseMessage then send LoginResponseMessage to messageClient and close connection.
 */
public class TokenResponsePlugin extends MessagePlugin<TokenResponseMessage> {

    private Logger logger;

    /**
     * 构造函数.
     */
    public TokenResponsePlugin() {
        super("TokenResponsePlugin", "send loginResponse message to client.");
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType().equals(MessageType.TokenResponse))
                && (context.getServer().getServerType() == ServerType.LoginServer);
    }

    @Override
    protected void processMessage(TokenResponseMessage message, PluginContext context) throws ClientNotFoundException {
        switch (context.getServer().getServerType()) {
            case LoginServer:
                processMessageWithLoginServer(message, context);
                break;
        }
    }

    private void processMessageWithLoginServer(TokenResponseMessage message, PluginContext context) {
        if (message.isSuccess()) {
            ConnectToken token = message.getToken();
            LoginResponseMessage loginResponseMessage = new LoginResponseMessage(token);
            context.getServer().messageProvider().send(token.getLoginId(), loginResponseMessage);
        } else {
            logger.error("core[" + message.getFromServer().getNodeId() + "] perhaps error! errorCode[" + message.getErrorCode() + "] errorMessage:" + message.getErrorMessage());
        }
    }
}
