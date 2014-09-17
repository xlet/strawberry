package cn.w.im.plugins.requestLinkedClients;

import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.client.MessageClientBasic;
import cn.w.im.domains.messages.server.ReadyMessage;
import cn.w.im.domains.messages.server.ResponseLinkedClientsMessage;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.core.server.MessageServer;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.core.plugins.MessagePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-17 下午3:39.
 * Summary: 处理请求所有已连接客户端消息响应消息插件.
 */
public class ResponseLinkedClientsPlugin extends MessagePlugin<ResponseLinkedClientsMessage> {

    private Logger logger;

    /**
     * 构造函数.
     */
    public ResponseLinkedClientsPlugin() {
        super("ResponseLinkedClientsPlugin", "request linked clients response message process.");
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.ResponseLinkedClients)
                && (context.getServer().getServerType() == ServerType.MessageServer);
    }

    @Override
    public void processMessage(ResponseLinkedClientsMessage message, PluginContext context) throws ClientNotFoundException {
        switch (context.getServer().getServerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
        }
    }

    private void processMessageWithMessageServer(ResponseLinkedClientsMessage message, PluginContext context) {
        MessageServer currentServer = (MessageServer) context.getServer();
        try {
            if (message.isSuccess()) {
                currentServer.respondProvider().receivedRespondedMessage(message);
                for (MessageClientBasic messageClientBasic : message.getLinkedClients()) {
                    currentServer.clientCacheProvider().registerClient(messageClientBasic, message.getMessageServer());
                }

                if (currentServer.respondProvider().allResponded(message.getRespondKey())) {
                    ReadyMessage readyMessage = new ReadyMessage(currentServer.getServerBasic());
                    currentServer.messageProvider().send(ServerType.LoginServer, readyMessage);
                }
            } else {
                logger.error("core[" + message.getFromServer().getNodeId() + "] perhaps error! errorCode[" + message.getErrorCode() + "] errorMessage:" + message.getErrorMessage());
                currentServer.respondProvider().interrupt(message.getRespondKey());
            }
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }

    }
}
