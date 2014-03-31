package cn.w.im.server;

import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.client.ServerClient;
import cn.w.im.domains.messages.server.ForwardMessage;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.server.RespondMessage;
import cn.w.im.exceptions.RegisteredRespondMessageException;
import cn.w.im.exceptions.RegisteredRespondServerException;
import cn.w.im.exceptions.ServerInnerException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午2:58.
 * Summary: implement SendMessageProvider.
 * <p/>
 * use ClientCacheProvider to search client and send to matched client.
 */
public class DefaultSendMessageProvider implements SendMessageProvider {

    private Log logger;

    private ClientCacheProvider clientCacheProvider;

    private RespondProvider respondProvider;

    private ServerBasic containerServer;

    /**
     * constructor.
     *
     * @param clientCacheProvider client cache provider.
     * @param respondProvider     respond provider.
     * @param containerServer     container server basic.
     */
    public DefaultSendMessageProvider(ClientCacheProvider clientCacheProvider, RespondProvider respondProvider, ServerBasic containerServer) {
        this.clientCacheProvider = clientCacheProvider;
        this.respondProvider = respondProvider;
        this.containerServer = containerServer;
        logger = LogFactory.getLog(this.getClass());
    }

    @Override
    public void send(String host, int port, Message message) {
        try {
            Client client = clientCacheProvider.getClient(host, port);
            sendMessage(client, message);
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void send(ServerType serverType, Message message) {
        try {
            Collection<Client> clients = clientCacheProvider.getClients(serverType);
            for (Client client : clients) {
                sendMessage(client, message);
            }
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void send(ServerBasic serverBasic, Message message) {
        try {
            Client client = clientCacheProvider.getClient(serverBasic);
            sendMessage(client, message);
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void send(String loginId, Message message) {
        try {
            Client client = clientCacheProvider.getClient(loginId);
            sendMessage(client, message);
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private void registeredRespond(Client client, Message message) throws RegisteredRespondMessageException, RegisteredRespondServerException {
        if ((message instanceof RespondMessage) && (client instanceof ServerClient)) {
            this.respondProvider.registerResponded(((RespondMessage) message).getRespondKey(), ((ServerClient) client).getServerBasic());
        }
    }

    private void sendMessage(Client client, Message message) throws ServerInnerException {
        registeredRespond(client, message);
        if ((this.containerServer.getServerType() != ServerType.MessageBus) && (client instanceof ServerClient)) {
            ForwardMessage forwardMessage = new ForwardMessage(this.containerServer, ((ServerClient) client).getServerBasic(), message);
            client.getContext().writeAndFlush(forwardMessage);
        } else if ((this.containerServer.getServerType() == ServerType.MessageBus) && (message instanceof ForwardMessage)) {
            ForwardMessage forwardMessage = (ForwardMessage) message;
            client.getContext().writeAndFlush(forwardMessage.getMessage());
        } else {
            client.getContext().writeAndFlush(message);
        }
    }
}
