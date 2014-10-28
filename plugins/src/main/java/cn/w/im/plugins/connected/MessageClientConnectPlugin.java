package cn.w.im.plugins.connected;

import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.*;
import cn.w.im.domains.client.MessageClientBasic;
import cn.w.im.domains.messages.client.ConnectMessage;
import cn.w.im.domains.messages.server.ConnectedMessage;
import cn.w.im.domains.messages.client.ConnectResponseMessage;
import cn.w.im.exceptions.*;
import cn.w.im.core.plugins.MessagePlugin;
import cn.w.im.core.server.MessageServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午7:34.
 * Summary: message client connect message server with connect token.
 */
public class MessageClientConnectPlugin extends MessagePlugin<ConnectMessage> {

    private final Logger logger;

    /**
     * 构造函数.
     */
    public MessageClientConnectPlugin() {
        super("MessageClientConnectPlugin", "message client connect message core with connect token.");
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.Connect)
                && (context.getServer().getServerType() == ServerType.MessageServer);
    }

    @Override
    protected void processMessage(ConnectMessage message, PluginContext context) throws ClientNotFoundException {
        switch (context.getServer().getServerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
        }
    }

    private void processMessageWithMessageServer(ConnectMessage message, PluginContext context) {
        MessageServer currentServer = (MessageServer) context.getServer();
        try {
            //register client, put into variety of maps...
            currentServer.clientCacheProvider().registerClient(message.getProductType(), message.getClientType(), message.getLoginId(), context.getCurrentHost(), context.getCurrentPort());
            currentServer.connect(message.getToken(), message.getLoginId(), context.getCurrentHost());

            MessageClientBasic messageClientBasic = new MessageClientBasic(message.getProductType(), message.getClientType(), message.getLoginId(), context.getCurrentHost(), context.getCurrentPort());
            ConnectedMessage connectedMessage = new ConnectedMessage(message.getToken(), messageClientBasic, currentServer.getServerBasic());

            currentServer.messageProvider().send(ServerType.MessageServer, connectedMessage);
            currentServer.messageProvider().send(ServerType.LoginServer, connectedMessage);
        } catch (TokenNotExistedException ex) {
            logger.info(ex.getMessage(), ex);
            ConnectResponseMessage errorResponse = new ConnectResponseMessage(ex.getErrorCode(), ex.getMessage());
            currentServer.messageProvider().send(message.getLoginId(), errorResponse);
        } catch (TokenErrorException ex) {
            logger.info(ex.getInnerMessage(), ex);
            ConnectResponseMessage errorResponse = new ConnectResponseMessage(ex.getErrorCode(), ex.getMessage());
            currentServer.messageProvider().send(message.getLoginId(), errorResponse);
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
