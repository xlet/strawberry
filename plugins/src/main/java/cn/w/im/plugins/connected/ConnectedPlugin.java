package cn.w.im.plugins.connected;

import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.*;
import cn.w.im.domains.messages.server.ConnectedMessage;
import cn.w.im.domains.messages.server.ConnectedResponseMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.core.plugins.MessagePlugin;
import cn.w.im.core.server.LoginServer;
import cn.w.im.core.server.MessageServer;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午9:22.
 * Summary: notify that message client connected message server.
 */
public class ConnectedPlugin extends MessagePlugin<ConnectedMessage> {
    /**
     * 构造函数.
     */
    public ConnectedPlugin() {
        super("ConnectedPlugin", "message client connected message core.");
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.Connected)
                && ((context.getServer().getServerType() == ServerType.MessageServer)
                || (context.getServer().getServerType() == ServerType.LoginServer));
    }

    @Override
    protected void processMessage(ConnectedMessage message, PluginContext context) throws ClientNotFoundException {
        switch (context.getServer().getServerType()) {
            case LoginServer:
                processMessageWithLoginServer(message, context);
                break;
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
        }
    }

    private void processMessageWithMessageServer(ConnectedMessage message, PluginContext context) {
        try {
            MessageServer currentServer = (MessageServer) context.getServer();
            currentServer.clientCacheProvider().registerClient(message.getConnectedClient(), message.getFromServer());
            ConnectedResponseMessage connectedResponseMessage = new ConnectedResponseMessage(message.getConnectedClient(), message.getToken(),
                    currentServer.getServerBasic(), message.getRespondKey());
            context.getServer().messageProvider().send(message.getFromServer(), connectedResponseMessage);
        } catch (ServerInnerException ex) {
            ConnectedResponseMessage errorResponse = new ConnectedResponseMessage(ex.getErrorCode(), ex.getMessage(),
                    context.getServer().getServerBasic(), message.getRespondKey());
            context.getServer().messageProvider().send(message.getFromServer(), errorResponse);
        }
    }

    private void processMessageWithLoginServer(ConnectedMessage message, PluginContext context) {
        LoginServer currentServer = (LoginServer) context.getServer();
        currentServer.allocateProvider().connected(message.getToken(), message.getConnectedClient(), message.getFromServer());
        ConnectedResponseMessage connectedResponseMessage = new ConnectedResponseMessage(message.getConnectedClient(), message.getToken(),
                currentServer.getServerBasic(), message.getRespondKey());
        currentServer.messageProvider().send(message.getFromServer(), connectedResponseMessage);
    }
}
