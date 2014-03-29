package cn.w.im.plugins.login;

import cn.w.im.domains.*;
import cn.w.im.domains.messages.server.TokenMessage;
import cn.w.im.domains.messages.server.TokenResponseMessage;
import cn.w.im.server.LoginServer;
import cn.w.im.server.MessageServer;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午5:11.
 * Summary: tokenMessage 处理插件.
 */
public class TokenPlugin extends MessagePlugin<TokenMessage> {

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public TokenPlugin(ServerType containerType) {
        super("TokenPlugin", "process login token message.", containerType);
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return context.getMessage().getMessageType() == MessageType.Token;
    }

    @Override
    public void processMessage(TokenMessage message, PluginContext context) throws NotSupportedServerTypeException, ClientNotFoundException {
        switch (this.containerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
            case LoginServer:
                processMessageWithLoginServer(message, context);
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithLoginServer(TokenMessage message, PluginContext context) {
        ConnectToken connectToken = message.getToken();
        LoginServer.current().allocateProvider().syncAllocation(connectToken);
    }

    private void processMessageWithMessageServer(TokenMessage message, PluginContext context) {
        ConnectToken token = message.getToken();
        MessageServer.current().addToken(token);

        TokenResponseMessage responseMessage = new TokenResponseMessage(token, MessageServer.current().getServerBasic(), message.getRespondKey());
        MessageServer.current().sendMessageProvider().send(ServerType.LoginServer, responseMessage);
    }
}
