package cn.w.im.plugins.messageServerReady;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.messages.server.ReadyMessage;
import cn.w.im.server.LoginServer;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-19 下午4:24.
 * Summary: process message server ready message.
 * only allowed to added to login server.
 * login server received message server ready message that means login server can allocation normal client to this message server.
 */
public class MessageServerReadyPlugin extends MessagePlugin<ReadyMessage> {
    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public MessageServerReadyPlugin(ServerType containerType) {
        super("MessageServerReadyPlugin", "message server ready.", containerType);
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return context.getMessage().getMessageType() == MessageType.Ready;
    }

    @Override
    public void processMessage(ReadyMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (containerType()) {
            case LoginServer:
                processMessageWithLoginServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(containerType());
        }
    }

    private void processMessageWithLoginServer(ReadyMessage readyMessage, PluginContext context) {
        ServerBasic readyMessageServer = readyMessage.getMessageServer();
        LoginServer.current().allocateProvider().messageServerReady(readyMessageServer);
    }
}
