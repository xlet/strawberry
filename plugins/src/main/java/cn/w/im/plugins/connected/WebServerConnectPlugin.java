package cn.w.im.plugins.connected;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.messages.client.ConnectResponseMessage;
import cn.w.im.domains.messages.client.WebServerConnectMessage;
import cn.w.im.exceptions.*;
import cn.w.im.plugins.MessagePlugin;
import cn.w.im.server.MessageServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jackie.
 */
public class WebServerConnectPlugin extends MessagePlugin<WebServerConnectMessage> {

    private final static String WEB_SERVER_CONNECT_TOKEN = "webServerToken1";
    private final Log logger;

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public WebServerConnectPlugin(ServerType containerType) {
        super("WebServerConnectPlugin", "web server connect message server with connect token.", containerType);
        logger = LogFactory.getLog(this.getClass());
    }

    @Override
    protected boolean isMatch(PluginContext context) {
        return context.getMessage().getMessageType() == MessageType.WebServerConnect;
    }

    @Override
    protected void processMessage(WebServerConnectMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (this.containerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(WebServerConnectMessage message, PluginContext context) {
        try {
            if (message.getToken().equals(WEB_SERVER_CONNECT_TOKEN)) {
                ConnectResponseMessage responseMessage = new ConnectResponseMessage();
                MessageServer.current().sendMessageProvider().send(context.getCurrentHost(), context.getCurrentPort(), responseMessage);
            }
            throw new TokenErrorException();
        } catch (TokenErrorException ex) {
            logger.info(ex.getMessage(), ex);
            ConnectResponseMessage errorResponse = new ConnectResponseMessage(ex.getErrorCode(), ex.getMessage());
            MessageServer.current().sendMessageProvider().send(context.getCurrentHost(), context.getCurrentPort(), errorResponse);
        }
    }
}
