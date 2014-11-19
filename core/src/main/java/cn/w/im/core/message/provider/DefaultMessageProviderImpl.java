package cn.w.im.core.message.provider;

import cn.w.im.core.Channel;
import cn.w.im.core.client.Client;
import cn.w.im.core.client.provider.ClientProvider;
import cn.w.im.core.client.ServerAsClient;
import cn.w.im.core.message.persistent.NormalMessagePersistentProvider;
import cn.w.im.core.persistent.PersistentProviderFactory;
import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.server.ServerType;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.message.Message;
import cn.w.im.core.message.MustRespondMessage;
import cn.w.im.core.message.client.NormalMessage;
import cn.w.im.core.message.server.ForwardMessage;
import cn.w.im.core.exception.ServerInnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * default implement {@link MessageProvider}
 */
public class DefaultMessageProviderImpl implements MessageProvider {

    private final static Logger LOG = LoggerFactory.getLogger(DefaultMessageProviderImpl.class);

    private NormalMessagePersistentProvider normalMessagePersistentProvider;

    private ClientProvider clientProvider;

    private RespondProvider respondProvider;

    private ServerBasic containerServer;

    /**
     * constructor.
     *
     * @param clientProvider  client cache provider.
     * @param respondProvider respond provider.
     * @param containerServer container server basic.
     */
    public DefaultMessageProviderImpl(ClientProvider clientProvider, RespondProvider respondProvider, ServerBasic containerServer) {
        this.clientProvider = clientProvider;
        this.respondProvider = respondProvider;
        this.containerServer = containerServer;
        try {
            normalMessagePersistentProvider = PersistentProviderFactory.getPersistentProvider(NormalMessagePersistentProvider.class);
        } catch (NullPointerException ex) {
            LOG.error("default message provider create error!", ex);
        } catch (ServerInnerException ex) {
            LOG.error("default message provider create error!", ex);
        }
    }

    @Override
    public Collection<NormalMessage> getOfflineMessages(BasicMember owner) {
        LOG.debug("get not received message by to=" + owner.getId());
        Collection<NormalMessage> messages = normalMessagePersistentProvider.getOfflineMessages(owner);
        LOG.debug("get " + messages.size() + " messages!");
        return messages;
    }

    @Override
    public void setMessageForwarded(BasicMember owner) {
        normalMessagePersistentProvider.setMessageForwarded(owner);
    }

    @Override
    public void send(Channel channel, Message message) {
        this.send(channel.host(), channel.port(), message);
    }

    @Override
    public void send(String host, int port, Message message) {
        try {
            Client client = clientProvider.getClient(host, port);
            sendMessage(client, message);
        } catch (ServerInnerException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void send(ServerType serverType, Message message) {
        try {
            Collection<Client> clients = clientProvider.getClient(serverType);
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
            Client client = clientProvider.getClient(serverBasic);
            sendMessage(client, message);
        } catch (ServerInnerException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void send(BasicMember member, Message message) {
        try {
            Collection<Client> clients = clientProvider.getClient(member);
            for (Client client : clients) {
                sendMessage(client, message);
            }
        } catch (ServerInnerException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void registeredRespond(Client client, Message message) throws RegisteredRespondMessageException, RegisteredRespondServerException {
        if ((message instanceof MustRespondMessage) && (client instanceof ServerAsClient)) {
            MustRespondMessage mustRespondMessage = (MustRespondMessage) message;
            ServerAsClient serverClient = (ServerAsClient) client;
            LOG.debug("respond register[" + mustRespondMessage.getRespondKey() + ":" + serverClient.getBasic().getNodeId() + "]");
            this.respondProvider.registerResponded(mustRespondMessage.getRespondKey(), serverClient.getBasic());
        }
    }

    private void sendMessage(Client client, Message message) throws ServerInnerException {
        registeredRespond(client, message);
        if ((this.containerServer.getServerType() != ServerType.MessageBus) && (client instanceof ServerAsClient)) {
            ServerAsClient serverClient = (ServerAsClient) client;
            LOG.debug("send to core[" + serverClient.getBasic().getNodeId() + "]");
            ForwardMessage forwardMessage = new ForwardMessage(this.containerServer, serverClient.getBasic(), message);
            client.send(forwardMessage);
        } else if ((this.containerServer.getServerType() == ServerType.MessageBus) && (message instanceof ForwardMessage)) {
            ForwardMessage forwardMessage = (ForwardMessage) message;
            LOG.debug("send to messageBus");
            client.send(forwardMessage.getMessage());
        } else {
            LOG.debug("send to client[" + client.host() + ":" + client.port() + "]");
            client.send(message);
        }

        if (message instanceof NormalMessage) {
            ((NormalMessage) message).setForward(true);
        }
    }
}
