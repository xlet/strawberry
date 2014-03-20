package cn.w.im.plugins.token;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.LoginToken;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.TokenMessage;
import cn.w.im.domains.server.MessageServer;
import cn.w.im.domains.server.ServerType;
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
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(TokenMessage message, PluginContext context) {
        LoginToken token = message.getToken();
        MessageServer.current().addToken(token);
    }
}
