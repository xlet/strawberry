package cn.w.im.core.providers.message;

import cn.w.im.core.providers.cache.client.ClientCacheProvider;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.client.ServerClient;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.MustRespondMessage;
import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.domains.messages.server.ForwardMessage;
import cn.w.im.exceptions.RegisteredRespondMessageException;
import cn.w.im.exceptions.RegisteredRespondServerException;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.persistent.NormalMessageDao;
import cn.w.im.persistent.PersistentRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * default implement {@link cn.w.im.core.providers.message.MessageProvider}
 */
public class DefaultMessageProviderImpl implements MessageProvider {

    private final static Logger LOG = LoggerFactory.getLogger(DefaultMessageProviderImpl.class);

    private NormalMessageDao normalMessageDao;

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
    public DefaultMessageProviderImpl(ClientCacheProvider clientCacheProvider, RespondProvider respondProvider, ServerBasic containerServer) {
        this.clientCacheProvider = clientCacheProvider;
        this.respondProvider = respondProvider;
        this.containerServer = containerServer;
        try {
            normalMessageDao = PersistentRepositoryFactory.getDao(NormalMessageDao.class);
        } catch (NullPointerException ex) {
            LOG.error("default message provider create error!", ex);
        } catch (ServerInnerException ex) {
            LOG.error("default message provider create error!", ex);
        }
    }

    @Override
    public List<NormalMessage> getOfflineMessages(String memberId) {
        LOG.debug("get not received message by to=" + memberId);
        List<NormalMessage> messages = normalMessageDao.getOfflineMessages(memberId);
        LOG.debug("get " + messages.size() + " messages!");
        return messages;
    }

    @Override
    public int setMessageForwarded(String memberId) {
        LOG.debug("set forwarded by to =" + memberId);
        int updateCount = normalMessageDao.setMessageForwarded(memberId);
        LOG.debug("set " + updateCount + " messages forwarded.");
        return updateCount;
    }

    @Override
    public void send(String host, int port, Message message) {
        try {
            Client client = clientCacheProvider.getClient(host, port);
            sendMessage(client, message);
        } catch (ServerInnerException ex) {
            LOG.error(ex.getMessage(), ex);
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
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void send(ServerBasic serverBasic, Message message) {
        try {
            Client client = clientCacheProvider.getClient(serverBasic);
            sendMessage(client, message);
        } catch (ServerInnerException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void send(String loginId, Message message) {
        try {
            Collection<Client> clients = clientCacheProvider.getClients(loginId);
            for (Client client : clients) {
                sendMessage(client, message);
            }
        } catch (ServerInnerException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void registeredRespond(Client client, Message message) throws RegisteredRespondMessageException, RegisteredRespondServerException {
        if ((message instanceof MustRespondMessage) && (client instanceof ServerClient)) {
            MustRespondMessage mustRespondMessage = (MustRespondMessage) message;
            ServerClient serverClient = (ServerClient) client;
            LOG.debug("respond register[" + mustRespondMessage.getRespondKey() + ":" + serverClient.getServerBasic().getNodeId() + "]");
            this.respondProvider.registerResponded(mustRespondMessage.getRespondKey(), serverClient.getServerBasic());
        }
    }

    private void sendMessage(Client client, Message message) throws ServerInnerException {
        registeredRespond(client, message);
        if ((this.containerServer.getServerType() != ServerType.MessageBus) && (client instanceof ServerClient)) {
            ServerClient serverClient = (ServerClient) client;
            LOG.debug("send to core[" + serverClient.getServerBasic().getNodeId() + "]");
            ForwardMessage forwardMessage = new ForwardMessage(this.containerServer, serverClient.getServerBasic(), message);
            client.getContext().writeAndFlush(forwardMessage);
        } else if ((this.containerServer.getServerType() == ServerType.MessageBus) && (message instanceof ForwardMessage)) {
            ForwardMessage forwardMessage = (ForwardMessage) message;
            LOG.debug("send to messageBus");
            client.getContext().writeAndFlush(forwardMessage.getMessage());
        } else {
            LOG.debug("send to client[" + client.getRemoteHost() + ":" + client.getRemotePort() + "]");
            client.getContext().writeAndFlush(message);
        }

        if (message instanceof NormalMessage) {
            ((NormalMessage) message).setForward(true);
        }
    }
}
