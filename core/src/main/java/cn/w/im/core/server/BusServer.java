package cn.w.im.core.server;

import cn.w.im.core.MessageHandlerContext;
import cn.w.im.core.client.Client;
import cn.w.im.core.client.ServerAsClient;
import cn.w.im.core.message.Message;
import cn.w.im.core.message.server.ForwardMessage;
import cn.w.im.core.message.server.ServerRegisterMessage;
import cn.w.im.core.message.server.ServerRegisterResponseMessage;
import cn.w.im.core.exception.ServerInnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午9:41.
 * Summary: 消息总线信息.
 */
public class BusServer extends AbstractServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusServer.class);

    public BusServer(int port) throws UnknownHostException {
        super(ServerType.MessageBus, port);
    }

    @Override
    public void handlerMessage(MessageHandlerContext context) {
        Message message = context.getMessage();
        switch (message.getMessageType()) {
            case ServerRegister:
                this.sendRegisteredServers(context);
                break;
            case Forward:
                ForwardMessage forwardMessage = (ForwardMessage) message;
                this.messageProvider().send(forwardMessage.getToServer(), message);
                break;
            default:
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("message[{}] is ignored!", message.getMessageType());
                }
                break;
        }
    }

    private void sendRegisteredServers(MessageHandlerContext context) {
        ServerRegisterMessage registerMessage = (ServerRegisterMessage) context.getMessage();
        ServerBasic registerServer = registerMessage.getServerBasic();
        String host = context.getHost();
        int port = context.getPort();
        try {
            //re-register this client to mark register server and client relation.
            this.clientProvider().registerClient(host, port, registerServer);

            //send registered server to register server.
            List<ServerBasic> startedServers = new ArrayList<ServerBasic>();
            Iterator<Client> registeredServerIterator = this.clientProvider().getAllServerClient().iterator();
            while (registeredServerIterator.hasNext()) {
                ServerAsClient serverClient = (ServerAsClient) registeredServerIterator.next();
                if (!serverClient.getBasic().getNodeId().equals(registerServer.getNodeId())) {
                    startedServers.add(serverClient.getBasic());
                }
            }
            ServerRegisterResponseMessage responseMessage = new ServerRegisterResponseMessage(startedServers, this.getServerBasic());
            this.messageProvider().send(registerServer, responseMessage);
        } catch (ServerInnerException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
