package cn.w.im.plugins.requestLinkedClients;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.client.MessageClientBasic;
import cn.w.im.domains.messages.server.ReadyMessage;
import cn.w.im.domains.messages.server.ResponseLinkedClientsMessage;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.core.server.MessageServer;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-17 下午3:39.
 * Summary: 处理请求所有已连接客户端消息响应消息插件.
 */
public class ResponseLinkedClientsPlugin extends MessagePlugin<ResponseLinkedClientsMessage> {

    private Log logger;

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public ResponseLinkedClientsPlugin(ServerType containerType) {
        super("ResponseLinkedClientsPlugin", "request linked clients response message process.", containerType);
        logger = LogFactory.getLog(this.getClass());
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return context.getMessage().getMessageType() == MessageType.ResponseLinkedClients;
    }

    @Override
    public void processMessage(ResponseLinkedClientsMessage message, PluginContext context) throws NotSupportedServerTypeException, ClientNotFoundException {
        switch (this.containerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(ResponseLinkedClientsMessage message, PluginContext context) {
        try {
            if (message.isSuccess()) {
                MessageServer.current().respondProvider().receivedRespondedMessage(message);
                for (MessageClientBasic messageClientBasic : message.getLinkedClients()) {
                    MessageServer.current().clientCacheProvider().registerClient(messageClientBasic, message.getMessageServer());
                }

                if (MessageServer.current().respondProvider().allResponded(message.getRespondKey())) {
                    ReadyMessage readyMessage = new ReadyMessage(MessageServer.current().getServerBasic());
                    MessageServer.current().messageProvider().send(ServerType.LoginServer, readyMessage);
                }
            } else {
                logger.error("core[" + message.getFromServer().getNodeId() + "] perhaps error! errorCode[" + message.getErrorCode() + "] errorMessage:" + message.getErrorMessage());
                MessageServer.current().respondProvider().interrupt(message.getRespondKey());
            }
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }

    }
}
