package cn.w.im.plugins.connected;

import cn.w.im.domains.*;
import cn.w.im.domains.client.MessageClientBasic;
import cn.w.im.domains.messages.client.ConnectMessage;
import cn.w.im.domains.messages.server.ConnectedMessage;
import cn.w.im.domains.messages.client.ConnectResponseMessage;
import cn.w.im.exceptions.*;
import cn.w.im.plugins.MessagePlugin;
import cn.w.im.server.MessageServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-24 下午7:34.
 * Summary: message client connect message server with connect token.
 */
public class MessageClientConnectPlugin extends MessagePlugin<ConnectMessage> {

    private final Log logger;

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public MessageClientConnectPlugin(ServerType containerType) {
        super("MessageClientConnectPlugin", "message client connect message server with connect token.", containerType);
        logger = LogFactory.getLog(this.getClass());
    }

    @Override
    protected boolean isMatch(PluginContext context) {
        return context.getMessage().getMessageType() == MessageType.Connect;
    }

    @Override
    protected void processMessage(ConnectMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (this.containerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(ConnectMessage message, PluginContext context) {
        try {
            MessageServer.current().clientCacheProvider().registerClient(message.getClientType(), message.getLoginId(), context.getCurrentHost(), context.getCurrentPort());
            MessageServer.current().connect(message.getToken(), message.getLoginId(), context.getCurrentHost());
            MessageClientBasic messageClientBasic = new MessageClientBasic(message.getClientType(), message.getLoginId(), context.getCurrentHost(), context.getCurrentPort());
            ConnectedMessage connectedMessage = new ConnectedMessage(message.getToken(), messageClientBasic, MessageServer.current().getServerBasic());

            MessageServer.current().sendMessageProvider().send(ServerType.MessageServer, connectedMessage);
            MessageServer.current().sendMessageProvider().send(ServerType.LoginServer, connectedMessage);
        } catch (TokenNotExistedException ex) {
            logger.info(ex.getMessage(), ex);
            ConnectResponseMessage errorResponse = new ConnectResponseMessage(ex.getErrorCode(), ex.getMessage());
            MessageServer.current().sendMessageProvider().send(message.getLoginId(), errorResponse);
        } catch (TokenErrorException ex) {
            logger.info(ex.getMessage(), ex);
            ConnectResponseMessage errorResponse = new ConnectResponseMessage(ex.getErrorCode(), ex.getMessage());
            MessageServer.current().sendMessageProvider().send(message.getLoginId(), errorResponse);
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
