package cn.w.im.plugins.serverRegister;

import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.messages.server.ReadyMessage;
import cn.w.im.domains.messages.server.RequestLinkedClientsMessage;
import cn.w.im.domains.messages.server.ServerRegisterResponseMessage;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.core.server.MessageServer;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.core.plugins.MessagePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午5:03.
 * Summary:
 */
public class MessageServerRegisterResponsePlugin extends MessagePlugin<ServerRegisterResponseMessage> {

    private Logger logger;

    /**
     * 构造函数.
     */
    public MessageServerRegisterResponsePlugin() {
        super("MessageServerRegisterResponsePlugin", "add response started message core to local and send request linked clients message to these message core.");
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.ServerRegisterResponse)
                && (context.getServer().getServerType() == ServerType.MessageServer);
    }

    @Override
    public void processMessage(ServerRegisterResponseMessage message, PluginContext context) throws ClientNotFoundException {
        switch (context.getServer().getServerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
        }
    }

    private void processMessageWithMessageServer(ServerRegisterResponseMessage message, PluginContext context) {
        MessageServer currentServer = (MessageServer) context.getServer();
        try {
            if (message.isSuccess()) {
                List<ServerBasic> startedServers = message.getStartedServers();
                List<ServerBasic> startedMessageServers = new ArrayList<ServerBasic>();
                for (ServerBasic serverBasic : startedServers) {
                    currentServer.clientCacheProvider().registerClient(serverBasic, context.getCurrentHost(), context.getCurrentPort());
                    if (serverBasic.getServerType() == ServerType.MessageServer) {
                        startedMessageServers.add(serverBasic);
                    }
                }
                if (startedMessageServers.size() == 0) {
                    ReadyMessage readyMessage = new ReadyMessage(currentServer.getServerBasic());
                    currentServer.messageProvider().send(ServerType.LoginServer, readyMessage);
                } else {
                    RequestLinkedClientsMessage requestMessage = new RequestLinkedClientsMessage(currentServer.getServerBasic());
                    currentServer.messageProvider().send(ServerType.MessageServer, requestMessage);
                }
            } else {
                logger.error("core[" + message.getFromServer().getNodeId() + "] perhaps error! errorCode[" + message.getErrorCode() + "] errorMessage:" + message.getErrorMessage());
            }
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
