package cn.w.im.plugins.token;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.LoginToken;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.TokenMessage;
import cn.w.im.domains.server.MessageServer;
import cn.w.im.domains.server.ServerType;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午5:11.
 * Summary: tokenMessage 处理插件.
 */
public class TokenPlugin extends MessagePlugin {

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public TokenPlugin(ServerType containerType) {
        super("TokenPlugin", "登陆token消息处理插件", containerType);
    }

    @Override
    public boolean isMatch(HandlerContext context) {
        return context.getMessage().getMessageType() == MessageType.Token;
    }

    @Override
    public void processMessage(Message message, HandlerContext context) {
        TokenMessage tokenMessage = (TokenMessage) message;
        switch (this.containerType()) {
            case MessageServer:
                processMessageWithMessageServer(tokenMessage, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(TokenMessage message, HandlerContext context) {
        LoginToken token = message.getToken();
        MessageServer.current().addToken(token);
    }
}