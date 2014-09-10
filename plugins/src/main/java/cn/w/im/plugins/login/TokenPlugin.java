package cn.w.im.plugins.login;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.MessageType;
import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.messages.server.TokenMessage;
import cn.w.im.domains.messages.server.TokenResponseMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.core.plugins.MessagePlugin;
import cn.w.im.core.server.LoginServer;
import cn.w.im.core.server.MessageServer;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午5:11.
 * Summary: tokenMessage 处理插件.
 */
public class TokenPlugin extends MessagePlugin<TokenMessage> {

    /**
     * 构造函数.
     */
    public TokenPlugin() {
        super("TokenPlugin", "process login token message.");
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.Token)
                && ((context.getServer().getServerType() == ServerType.MessageServer)
                || (context.getServer().getServerType() == ServerType.LoginServer));
    }

    @Override
    public void processMessage(TokenMessage message, PluginContext context) throws ClientNotFoundException {
        switch (context.getServer().getServerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
            case LoginServer:
                processMessageWithLoginServer(message, context);
                break;
        }
    }

    private void processMessageWithLoginServer(TokenMessage message, PluginContext context) {
        LoginServer currentServer = (LoginServer) context.getServer();
        ConnectToken connectToken = message.getToken();
        currentServer.allocateProvider().syncAllocation(connectToken);
    }

    private void processMessageWithMessageServer(TokenMessage message, PluginContext context) {
        MessageServer currentServer = (MessageServer) context.getServer();
        ConnectToken token = message.getToken();
        currentServer.addToken(token);

        TokenResponseMessage responseMessage = new TokenResponseMessage(token, currentServer.getServerBasic(), message.getRespondKey());
        currentServer.messageProvider().send(ServerType.LoginServer, responseMessage);
    }
}
