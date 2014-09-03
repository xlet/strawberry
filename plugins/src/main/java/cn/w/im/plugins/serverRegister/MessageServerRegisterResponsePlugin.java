package cn.w.im.plugins.serverRegister;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.messages.server.ReadyMessage;
import cn.w.im.domains.messages.server.RequestLinkedClientsMessage;
import cn.w.im.domains.messages.server.ServerRegisterResponseMessage;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.core.server.MessageServer;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午5:03.
 * Summary:
 */
public class MessageServerRegisterResponsePlugin extends MessagePlugin<ServerRegisterResponseMessage> {

    private Log logger;

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public MessageServerRegisterResponsePlugin(ServerType containerType) {
        super("MessageServerRegisterResponsePlugin", "add response started message core to local and send request linked clients message to these message core.", containerType);
        logger = LogFactory.getLog(this.getClass());
    }

    @Override
    public boolean isMatch(PluginContext context) {
        boolean isMatch = context.getMessage().getMessageType() == MessageType.ServerRegisterResponse;
        return isMatch;
    }

    @Override
    public void processMessage(ServerRegisterResponseMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (this.containerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(ServerRegisterResponseMessage message, PluginContext context) {
        try {
            if (message.isSuccess()) {
                List<ServerBasic> startedServers = message.getStartedServers();
                List<ServerBasic> startedMessageServers = new ArrayList<ServerBasic>();
                for (ServerBasic serverBasic : startedServers) {
                    MessageServer.current().clientCacheProvider().registerClient(serverBasic, context.getCurrentHost(), context.getCurrentPort());
                    if (serverBasic.getServerType() == ServerType.MessageServer) {
                        startedMessageServers.add(serverBasic);
                    }
                }
                if (startedMessageServers.size() == 0) {
                    ReadyMessage readyMessage = new ReadyMessage(MessageServer.current().getServerBasic());
                    MessageServer.current().messageProvider().send(ServerType.LoginServer, readyMessage);
                } else {
                    RequestLinkedClientsMessage requestMessage = new RequestLinkedClientsMessage(MessageServer.current().getServerBasic());
                    MessageServer.current().messageProvider().send(ServerType.MessageServer, requestMessage);
                }
            } else {
                logger.error("core[" + message.getFromServer().getNodeId() + "] perhaps error! errorCode[" + message.getErrorCode() + "] errorMessage:" + message.getErrorMessage());
            }
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
