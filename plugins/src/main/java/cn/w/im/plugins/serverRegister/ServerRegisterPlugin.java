package cn.w.im.plugins.serverRegister;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.client.ServerClient;
import cn.w.im.domains.messages.server.ServerRegisterMessage;
import cn.w.im.domains.messages.server.ServerRegisterResponseMessage;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.server.MessageBus;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午7:22.
 * Summary: process ServerRegisterMessage
 * add server to message bus.
 * <p/>
 * this plugin only add to message bus server.
 */
public class ServerRegisterPlugin extends MessagePlugin<ServerRegisterMessage> {

    private Log logger;

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public ServerRegisterPlugin(ServerType containerType) {
        super("serverRegisterPlugin", "server register to message bus server.", containerType);
        logger = LogFactory.getLog(this.getClass());
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return context.getMessage().getMessageType() == MessageType.ServerRegister;
    }

    @Override
    public void processMessage(ServerRegisterMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (containerType()) {
            case MessageBus:
                processMessageWithMessageBus(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageBus(ServerRegisterMessage registerMessage, PluginContext context) throws NotSupportedServerTypeException {

        ServerBasic serverBasic = registerMessage.getServerBasic();
        try {
            //注册服务
            MessageBus.current().clientCacheProvider().registerClient(serverBasic, context.getCurrentHost(), context.getCurrentPort());

            //ToDo:jackie split this plugin to two plugin(register plugin and response plugin)

            //回复
            List<ServerBasic> startedServers = new ArrayList<ServerBasic>();
            Iterator<Client> registeredServerIterator = MessageBus.current().clientCacheProvider().getAllServerClients().iterator();
            while (registeredServerIterator.hasNext()) {
                ServerClient serverClient = (ServerClient) registeredServerIterator.next();
                if (!serverClient.getServerBasic().getNodeId().equals(serverBasic.getNodeId())) {
                    startedServers.add(serverClient.getServerBasic());
                }
            }
            ServerRegisterResponseMessage responseMessage = new ServerRegisterResponseMessage(startedServers, MessageBus.current().getServerBasic());
            MessageBus.current().sendMessageProvider().send(serverBasic, responseMessage);
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
