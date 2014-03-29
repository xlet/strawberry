package cn.w.im.plugins.connected;

import cn.w.im.domains.*;
import cn.w.im.domains.messages.server.ConnectedMessage;
import cn.w.im.domains.messages.server.ConnectedResponseMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.plugins.MessagePlugin;
import cn.w.im.server.LoginServer;
import cn.w.im.server.MessageServer;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午9:22.
 * Summary: notify that message client connected message server.
 */
public class ConnectedPlugin extends MessagePlugin<ConnectedMessage> {
    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public ConnectedPlugin(ServerType containerType) {
        super("ConnectedPlugin", "message client connected message server.", containerType);
    }

    @Override
    protected boolean isMatch(PluginContext context) {
        return context.getMessage().getMessageType() == MessageType.Connected;
    }

    @Override
    protected void processMessage(ConnectedMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (this.containerType()) {
            case LoginServer:
                processMessageWithLoginServer(message, context);
                break;
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(ConnectedMessage message, PluginContext context) {
        try {
            MessageServer.current().clientCacheProvider().registerClient(message.getMessageClientBasic(), message.getFromServer());
            ConnectedResponseMessage connectedResponseMessage = new ConnectedResponseMessage(message.getToken(), LoginServer.current().getServerBasic(), message.getRespondKey());
            MessageServer.current().sendMessageProvider().send(message.getFromServer(), connectedResponseMessage);
        } catch (ServerInnerException ex) {
            ConnectedResponseMessage errorResponse = new ConnectedResponseMessage(ex.getErrorCode(), ex.getMessage(), MessageServer.current().getServerBasic(), message.getRespondKey());
            MessageServer.current().sendMessageProvider().send(message.getFromServer(), errorResponse);
        }
    }

    private void processMessageWithLoginServer(ConnectedMessage message, PluginContext context) {
        LoginServer.current().allocateProvider().connected(message.getToken(), message.getFromServer());
        ConnectedResponseMessage connectedResponseMessage = new ConnectedResponseMessage(message.getToken(), LoginServer.current().getServerBasic(), message.getRespondKey());
        LoginServer.current().sendMessageProvider().send(message.getFromServer(), connectedResponseMessage);
    }
}
