package cn.w.im.core.client.provider;

import cn.w.im.core.Channel;
import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.client.*;
import cn.w.im.core.server.AbstractServer;
import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.server.ServerType;
import cn.w.im.core.exception.*;
import cn.w.im.core.member.BasicMember;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * default implement of client provider.
 */
public class DefaultClientProvider implements ClientProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultClientProvider.class);

    private AtomicInteger cachedClients = new AtomicInteger(0);

    private Collection<ClientRemoveListener> clientRemoveListeners;

    /**
     * all clients.
     * key:host.
     * inner key: port.
     */
    private Map<String, Map<Integer, Client>> clientMap;

    /**
     * all server as clients.
     * key:server node  id.
     */
    private Map<String, Client> serverClientMap;

    /**
     * all message clients.
     * key:member id.
     * inner key: client type.
     */
    private Map<String, Map<MessageClientType, Client>> messageClientMap;

    /**
     * constructor.
     */
    public DefaultClientProvider() {
        this.clientMap = new ConcurrentHashMap<String, Map<Integer, Client>>();
        this.serverClientMap = new ConcurrentHashMap<String, Client>();
        this.messageClientMap = new ConcurrentHashMap<String, Map<MessageClientType, Client>>();
        this.clientRemoveListeners = new CopyOnWriteArrayList<ClientRemoveListener>();
    }

    @Override
    public final void registerClient(Channel channel, AbstractServer connectedServer) throws ClientRegisteredException {

        if (channel == null) {
            throw new IllegalArgumentException("channel is null");
        }

        String host = channel.host();
        int port = channel.port();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("try register client[host:{},port:{}].", host, port);
        }

        this.printAllClients();

        Client client = new NoTypeClient(channel, connectedServer.getServerBasic());
        this.registerClient(host, port, client);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("client[{}] registered.", client.key());
        }

        this.printAllClients();
    }

    @Override
    public final void registerClient(String host, int port, BasicMember member, MessageClientType clientType)
            throws MessageClientRegisteredException, ClientNotRegisterException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("try to register message client[host:{},port:{},memberId:{},clientType:{}]",
                    host, port, member.getId(), clientType);
        }

        this.printAllClients();

        // check basic client has registered.
        if (!this.basicClientExists(host, port)) {
            throw new ClientNotRegisterException(host, port);
        }
        //check message client has registered.
        if (this.messageClientExists(member, clientType)) {
            throw new MessageClientRegisteredException(clientType, member.getId());
        }
        try {

            Client client = this.createMessageClient(host, port, member, clientType);
            //change basic client instance.
            this.changeBasic(host, port, client);
            //add client to message client map.
            if (this.messageClientMap.containsKey(member.getId())) {
                Map<MessageClientType, Client> clientTypeMap = this.messageClientMap.get(member.getId());
                clientTypeMap.put(clientType, client);
            } else {
                Map<MessageClientType, Client> clientTypeMap = new ConcurrentHashMap<MessageClientType, Client>();
                clientTypeMap.put(clientType, client);
                this.messageClientMap.put(member.getId(), clientTypeMap);
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("message client[memberId:{},clientType:{}] registered.", member.getId(), clientType);
            }
        } catch (ClientNotFoundException e) {
            throw new ClientNotRegisterException(host, port, e);
        }

        this.printAllClients();
    }

    @Override
    public final void registerClient(String host, int port, ServerBasic serverBasic)
            throws ServerRegisteredException, ClientNotRegisterException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("try to register server client[host:{},port:{},server:{}]",
                    host, port, serverBasic.getNodeId());
        }

        this.printAllClients();

        // check basic client has registered.
        if (!this.basicClientExists(host, port)) {
            throw new ClientNotRegisterException(host, port);
        }
        //check server client has registered.
        if (this.serverClientExists(serverBasic)) {
            throw new ServerRegisteredException(serverBasic);
        }

        try {

            Client client = this.createServerAsClient(host, port, serverBasic);
            //change basic client instance.
            this.changeBasic(host, port, client);
            //add client to message client map.
            if (!this.serverClientMap.containsKey(serverBasic.getNodeId())) {
                this.serverClientMap.put(serverBasic.getNodeId(), client);
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("server client[server:{}] registered.", serverBasic.getNodeId());
            }
        } catch (ClientNotFoundException e) {
            throw new ClientNotRegisterException(host, port, e);
        }

        this.printAllClients();
    }

    private ServerAsClient createServerAsClient(String host, int port, ServerBasic serverBasic) throws ClientNotFoundException {
        Client noTypeClient = this.getClient(host, port);
        ServerBasic connectedServer = noTypeClient.connectedServer();
        Channel channel = noTypeClient.channel();
        ServerAsClient client = new ServerAsClient(channel, connectedServer, serverBasic);
        return client;
    }

    private MessageClient createMessageClient(String host, int port, BasicMember member, MessageClientType clientType)
            throws ClientNotFoundException {
        Client noTypeClient = this.getClient(host, port);
        ServerBasic connectedServer = noTypeClient.connectedServer();
        Channel channel = noTypeClient.channel();
        MessageClient client = new MessageClient(channel, connectedServer, member, clientType, UUID.randomUUID().toString());
        return client;
    }

    private void registerClient(String host, int port, Client client) throws ClientRegisteredException {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("try re-register client[host:{},port:{},client:{}", host, port, client);
        }

        if (this.clientMap.containsKey(host)) {
            Map<Integer, Client> portClientMap = this.clientMap.get(host);
            if (portClientMap.containsKey(port)) {
                throw new ClientRegisteredException(host, port);
            }
            portClientMap.put(port, client);
        } else {
            Map<Integer, Client> portClientMap = new ConcurrentHashMap<Integer, Client>();
            portClientMap.put(port, client);
            this.clientMap.put(host, portClientMap);
        }

        this.cachedClients.getAndIncrement();
    }

    private boolean basicClientExists(String host, int port) {
        try {
            this.getClient(host, port);
            return true;
        } catch (ClientNotFoundException ex) {
            return false;
        }
    }

    private void changeBasic(String host, int port, Client client) {
        try {
            this.removeClient(host, port);
            this.registerClient(host, port, client);

        } catch (ServerInnerException ex) {
            LOGGER.error("change basic error,this client is not existed.", ex);
        }
    }


    private boolean messageClientExists(BasicMember member, MessageClientType clientType) {
        try {
            this.getClient(member, clientType);
            return true;
        } catch (ClientNotFoundException ex) {
            return false;
        }
    }


    private boolean serverClientExists(ServerBasic basic) {
        try {
            this.getClient(basic);
            return true;
        } catch (ClientNotFoundException ex) {
            return false;
        }
    }

    @Override
    public final void removeClient(Channel channel) throws ServerInnerException {

        if (channel == null) {
            throw new IllegalArgumentException("channel is null.");
        }

        String host = channel.host();
        int port = channel.port();
        this.removeClient(host, port);

    }

    @Override
    public void removeClient(String host, int port) throws ServerInnerException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("try to remove client[host:{},port:{}", host, port);
        }

        this.printAllClients();

        try {
            Client client = this.getClient(host, port);

            this.fireClientRemove(client);

            this.removeBasicClient(client);
            if (client instanceof MessageClient) {
                MessageClient messageClient = (MessageClient) client;
                this.removeMessageClient(messageClient);
            }
            if (client instanceof ServerAsClient) {
                ServerAsClient serverAsClient = (ServerAsClient) client;
                this.removeServerAsClient(serverAsClient);
            }
        } catch (ClientNotFoundException ex) {
            throw new ClientNotRegisterException(host, port, ex);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("client[host:{},port,{}] removed.", host, port);
        }

        this.cachedClients.getAndDecrement();

        this.printAllClients();
    }

    public void fireClientRemove(Client client) throws ServerInnerException {
        for (ClientRemoveListener listener : this.clientRemoveListeners) {
            listener.onClientRemove(client);
        }
    }

    @Override
    public void registerClientRemoveListener(ClientRemoveListener clientRemoveListener) {
        if (!this.clientRemoveListeners.contains(clientRemoveListener)) {
            this.clientRemoveListeners.add(clientRemoveListener);
        }
    }

    private void removeBasicClient(Client client) {
        String host = client.host();
        int port = client.port();
        if (this.clientMap.containsKey(host)) {
            Map<Integer, Client> portClientMap = this.clientMap.get(host);
            portClientMap.remove(port);
            if (portClientMap.isEmpty()) {
                this.clientMap.remove(host);
            }
        }
    }

    private void removeMessageClient(MessageClient client) {
        String memberId = client.getMember().getId();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("try to remove message client[memberId:{}].", memberId);
        }

        MessageClientType clientType = client.getClientType();
        if (this.messageClientMap.containsKey(memberId)) {
            Map<MessageClientType, Client> clientTypeMap = this.messageClientMap.get(memberId);
            clientTypeMap.remove(clientType);
            if (clientTypeMap.isEmpty()) {
                this.messageClientMap.remove(memberId);
            }
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("message client [memberId:{}] removed.", memberId);
        }
    }

    private void removeServerAsClient(ServerAsClient client) {
        String serverNodeId = client.getBasic().getNodeId();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("try to remove server as client[nodeId:{}].", serverNodeId);
        }
        this.serverClientMap.remove(serverNodeId);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("server as client[nodeId:{}] removed.", serverNodeId);
        }
    }

    @Override
    public Client getClient(Channel channel) throws ClientNotFoundException {
        if (channel == null) {
            throw new IllegalArgumentException("channel is null.");
        }

        String host = channel.host();
        int port = channel.port();

        return this.getClient(host, port);

    }

    @Override
    public Client getClient(String host, int port) throws ClientNotFoundException {
        if (this.clientMap.containsKey(host)) {
            Map<Integer, Client> portClientMap = this.clientMap.get(host);
            if (portClientMap.containsKey(port)) {
                return portClientMap.get(port);
            }
        }
        throw new ClientNotFoundException(host, port);
    }

    @Override
    public Client getClient(ServerBasic serverBasic) throws ClientNotFoundException {
        if (this.serverClientMap.containsKey(serverBasic.getNodeId())) {
            return this.serverClientMap.get(serverBasic.getNodeId());
        }
        throw new ClientNotFoundException(serverBasic);
    }

    @Override
    public Client getClient(BasicMember member, MessageClientType clientType) throws ClientNotFoundException {
        if (this.messageClientMap.containsKey(member.getId())) {
            Map<MessageClientType, Client> clientTypeMap = this.messageClientMap.get(member.getId());
            if (clientTypeMap.containsKey(clientType)) {
                return clientTypeMap.get(clientType);
            }
        }
        throw new ClientNotFoundException(member.getId(), clientType);
    }

    @Override
    public Collection<Client> getClient(BasicMember member) {
        if (this.messageClientMap.containsKey(member.getId())) {
            return this.messageClientMap.get(member.getId()).values();
        }
        return new ArrayList<Client>();
    }

    @Override
    public Collection<Client> getClient(ServerType serverType) {
        Collection<Client> matchedServerClients = new ArrayList<Client>();
        Collection<Client> allServerClients = this.serverClientMap.values();
        for (Client client : allServerClients) {
            if (client instanceof ServerAsClient) {
                ServerAsClient serverClient = (ServerAsClient) client;
                if (serverClient.getBasic().getServerType().equals(serverType)) {
                    matchedServerClients.add(serverClient);
                }
            }
        }
        return matchedServerClients;
    }

    @Override
    public Collection<Client> getAllServerClient() {
        return this.serverClientMap.values();
    }

    private static final String NEWLINE = String.format("%n");

    private void printAllClients() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("now cached client:{} clients", this.cachedClients.intValue());
            StringBuilder allClientString = new StringBuilder();
            allClientString.append(NEWLINE).append("now cached client:" + this.cachedClients.intValue() + " clients").append(NEWLINE);
            for (Map.Entry<String, Map<Integer, Client>> portEntry : clientMap.entrySet()) {
                StringBuilder sb = build(portEntry.getKey(), portEntry.getValue());
                allClientString.append(sb);
            }
            LOGGER.debug(allClientString.toString());
        }
    }

    private <K, V> StringBuilder build(String keyName, Map<K, V> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(NEWLINE).append("+----------------------------------+");
        sb.append(NEWLINE).append("            " + keyName);
        sb.append(NEWLINE).append("+---------+-------------------------+");
        for (Map.Entry<K, V> entry : map.entrySet()) {
            sb.append(NEWLINE).append("|\t").append(entry.getKey()).append("\t|\t").append(entry.getValue()).append("\t|");
        }
        sb.append(NEWLINE).append("+---------+-------------------------+");
        return sb;
    }
}
