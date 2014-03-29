package cn.w.im.server;

import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.client.MessageClient;
import cn.w.im.domains.client.MessageClientBasic;
import cn.w.im.domains.client.ServerClient;
import cn.w.im.exceptions.*;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午3:34.
 * Summary: implement ClientCacheProvider.
 */
public class DefaultClientCacheProvider implements ClientCacheProvider {

    /**
     * key:linkedHost.
     * value key:linkedPort.
     */
    private Map<String, Map<Integer, Client>> clientMap;

    /**
     * key:server node id.
     */
    private Map<String, ServerBasic> startedServerMap;

    /**
     * key:server node id.
     */
    private Map<String, Client> serverClientMap;

    /**
     * key:login id.
     */
    private Map<String, Client> messageClientMap;

    /**
     * key login id.
     */
    private Map<String, ServerBasic> otherServerClientMap;

    /**
     * constructor.
     */
    public DefaultClientCacheProvider() {
        this.clientMap = new ConcurrentHashMap<String, Map<Integer, Client>>();
        this.startedServerMap = new ConcurrentHashMap<String, ServerBasic>();
        this.serverClientMap = new ConcurrentHashMap<String, Client>();
        this.messageClientMap = new ConcurrentHashMap<String, Client>();
    }

    @Override
    public void registerClient(ChannelHandlerContext context) throws ClientRegisteredException {
        Client client = new Client(context);
        if (this.clientMap.containsKey(client.getRemoteHost())) {
            Map<Integer, Client> portClients = this.clientMap.get(client.getRemoteHost());
            if (portClients.containsKey(client.getRemotePort())) {
                throw new ClientRegisteredException(client.getRemoteHost(), client.getRemotePort());
            }
            portClients.put(client.getRemotePort(), client);
        } else {
            Map<Integer, Client> portClients = new ConcurrentHashMap<Integer, Client>();
            portClients.put(client.getRemotePort(), client);
            this.clientMap.put(client.getRemoteHost(), portClients);
        }
    }

    @Override
    public void registerClient(String loginId, String linkedHost, int linkedPort) throws MessageClientRegisteredException, ClientNotRegisterException {
        if (this.messageClientMap.containsKey(loginId)) {
            throw new MessageClientRegisteredException(loginId);
        }
        if (this.clientMap.containsKey(linkedHost)) {
            Map<Integer, Client> portClientMap = this.clientMap.get(linkedHost);
            if (portClientMap.containsKey(linkedPort)) {
                Client registeredClient = portClientMap.get(linkedPort);
                registeredClient = new MessageClient(registeredClient.getContext(), loginId);
                portClientMap.remove(linkedPort);
                portClientMap.put(linkedPort, registeredClient);
                this.messageClientMap.put(loginId, registeredClient);
            } else {
                throw new ClientNotRegisterException(linkedHost, linkedPort);
            }
        } else {
            throw new ClientNotRegisterException(linkedHost, linkedPort);
        }
    }

    @Override
    public void registerClient(MessageClientBasic messageClientBasic, ServerBasic serverBasic) throws MessageClientRegisteredException {
        if (this.otherServerClientMap.containsKey(messageClientBasic.getLoginId())) {
            throw new MessageClientRegisteredException(messageClientBasic.getLoginId());
        }
        this.otherServerClientMap.put(messageClientBasic.getLoginId(), serverBasic);
    }

    @Override
    public void registerClient(ServerBasic serverBasic, String linkedHost, int linkedPort) throws ServerRegisteredException, ClientNotRegisterException {

        if (this.serverClientMap.containsKey(serverBasic.getNodeId())) {
            throw new ServerRegisteredException(serverBasic);
        }

        if (this.startedServerMap.containsKey(serverBasic.getNodeId())) {
            throw new ServerRegisteredException(serverBasic);
        }

        if (this.clientMap.containsKey(linkedHost)) {
            Map<Integer, Client> portClientMap = this.clientMap.get(linkedHost);
            if (portClientMap.containsKey(linkedPort)) {
                Client registeredClient = portClientMap.get(linkedPort);
                registeredClient = new ServerClient(registeredClient.getContext(), serverBasic);
                portClientMap.remove(linkedPort);
                portClientMap.put(registeredClient.getRemotePort(), registeredClient);
                this.serverClientMap.put(serverBasic.getNodeId(), registeredClient);
                this.startedServerMap.put(serverBasic.getNodeId(), serverBasic);
            } else {
                throw new ClientNotRegisterException(linkedHost, linkedPort);
            }
        } else {
            throw new ClientNotRegisterException(linkedHost, linkedPort);
        }
    }

    @Override
    public void removeClient(ChannelHandlerContext context) throws ServerNotRegisterException, ClientNotRegisterException {
        Client client = new Client(context);
        removeClient(client.getRemoteHost(), client.getRemotePort());
    }

    @Override
    public void removeClient(String host, int port) throws ServerNotRegisterException, ClientNotRegisterException {
        if (this.clientMap.containsKey(host)) {
            Map<Integer, Client> portClientMap = this.clientMap.get(host);
            if (portClientMap.containsKey(port)) {
                Client removeClient = portClientMap.get(port);
                removeServerClientMap(removeClient);
                removeMessageClientMap(removeClient);
                portClientMap.remove(port);
            } else {
                throw new ClientNotRegisterException(host, port);
            }
        } else {
            throw new ClientNotRegisterException(host, port);
        }
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
    public Client getClient(String loginId) throws ClientNotFoundException {
        if (this.messageClientMap.containsKey(loginId)) {
            return this.messageClientMap.get(loginId);
        }
        if (this.otherServerClientMap.containsKey(loginId)) {
            ServerBasic serverBasic = this.otherServerClientMap.get(loginId);
            return getClient(serverBasic);
        }
        throw new ClientNotFoundException(loginId);
    }

    @Override
    public Collection<Client> getClients(ServerType serverType) {
        List<Client> clients = new ArrayList<Client>();
        for (String key : this.startedServerMap.keySet()) {
            ServerBasic serverBasic = this.startedServerMap.get(key);
            if ((serverBasic.getServerType() == serverType) && (this.serverClientMap.containsKey(key))) {
                clients.add(this.serverClientMap.get(key));
            }
        }
        return clients;
    }

    @Override
    public Collection<Client> getAllMessageClients() {
        return this.messageClientMap.values();
    }

    @Override
    public Collection<Client> getAllServerClients() {
        return this.serverClientMap.values();
    }

    private void removeServerClientMap(Client removeClient) throws ServerNotRegisterException {
        String deleteKey = "";
        for (String key : this.serverClientMap.keySet()) {
            Client currentClient = this.serverClientMap.get(key);
            if (currentClient.equals(removeClient)) {
                deleteKey = key;
                break;
            }
        }
        if (deleteKey != "") {
            if (this.startedServerMap.containsKey(deleteKey)) {
                this.startedServerMap.remove(deleteKey);
            } else {
                throw new ServerNotRegisterException(deleteKey);
            }
            this.serverClientMap.remove(deleteKey);
        }
    }

    private void removeMessageClientMap(Client removeClient) {
        String deleteKey = "";
        for (String key : this.messageClientMap.keySet()) {
            Client currentClient = this.messageClientMap.get(key);
            if (currentClient.equals(removeClient)) {
                deleteKey = key;
                break;
            }
        }
        if (deleteKey != "") {
            this.serverClientMap.remove(deleteKey);
        }
    }


}
