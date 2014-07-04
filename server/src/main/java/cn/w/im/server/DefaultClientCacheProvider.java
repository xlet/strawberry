package cn.w.im.server;

import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.client.*;
import cn.w.im.exceptions.*;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午3:34.
 * Summary: implement ClientCacheProvider.
 */
public class DefaultClientCacheProvider implements ClientCacheProvider {

    private Log logger;

    /**
     * all linked clients contains serverClient and message Client.
     * key:linkedHost.
     * value key:linkedPort.
     */
    private Map<String, Map<Integer, Client>> clientMap;

    /**
     * all serverClient who linked on this server.
     * key:server node id.
     */
    private Map<String, Client> serverClientMap;

    /**
     * message clients linked on this server.
     * key:login id.
     * value:map{key:messageClientType,value:client}
     */
    private Map<String, Map<MessageClientType, Client>> messageClientOnThisServerMap;

    /**
     * message clients linked on other server.
     * key:server node id.
     * value:map{key:login id,value:map{key:messageClientType,value:MessageClientBasic Map}}.
     */
    private Map<String, Map<String, Map<MessageClientType, MessageClientBasic>>> messageClientOnOtherServerMap;


    private void dump() {
        logger.debug("dumping clientMap start---");
        for (Map.Entry<String, Map<Integer, Client>> portEntry : clientMap.entrySet()) {
            printMap("hostip" + portEntry.getKey(), portEntry.getValue());
        }
        logger.debug("dumping clientMap end---");

        logger.debug("dumping messageClientOnThisServerMap start---");
        for (Map.Entry<String, Map<MessageClientType, Client>> entry : messageClientOnThisServerMap.entrySet()) {

            printMap("loginId" + entry.getKey(), entry.getValue());

        }
        logger.debug("dumping messageClientOnThisServerMap end---");

    }

    private <K, V> void printMap(String keyName, Map<K, V> map) {
        logger.debug("keyName=" + keyName);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            logger.debug(entry.getKey() + "=" + entry.getValue().toString());
        }
    }


    /**
     * constructor.
     */
    public DefaultClientCacheProvider() {
        this.logger = LogFactory.getLog(this.getClass());
        this.clientMap = new ConcurrentHashMap<String, Map<Integer, Client>>();
        this.serverClientMap = new ConcurrentHashMap<String, Client>();
        this.messageClientOnThisServerMap = new ConcurrentHashMap<String, Map<MessageClientType, Client>>();
        this.messageClientOnOtherServerMap = new ConcurrentHashMap<String, Map<String, Map<MessageClientType, MessageClientBasic>>>();
    }

    @Override
    public void registerClient(ChannelHandlerContext context) throws ClientRegisteredException {
        Client client = new Client(context);
        logger.debug("register client[" + client.getKey() + "]");
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
    public void registerClient(MessageClientType messageClientType, String loginId, String linkedHost, int linkedPort) throws MessageClientRegisteredException, ClientNotRegisterException {
        logger.debug("register message client:" + loginId + "[" + linkedHost + ":" + linkedPort + "]");
        Map<MessageClientType, Client> clientTypeClientMap = null;
        if (this.messageClientOnThisServerMap.containsKey(loginId)) {
            clientTypeClientMap = this.messageClientOnThisServerMap.get(loginId);
            if (clientTypeClientMap.containsKey(messageClientType)) {
                throw new MessageClientRegisteredException(messageClientType, loginId);
            }
        }
        if (this.clientMap.containsKey(linkedHost)) {
            Map<Integer, Client> portClientMap = this.clientMap.get(linkedHost);
            if (portClientMap.containsKey(linkedPort)) {
                Client registeredClient = portClientMap.get(linkedPort);
                registeredClient = new MessageClient(registeredClient.getContext(), messageClientType, loginId);
                portClientMap.remove(linkedPort);
                portClientMap.put(linkedPort, registeredClient);
                if (clientTypeClientMap != null) {
                    clientTypeClientMap.put(messageClientType, registeredClient);
                } else {
                    clientTypeClientMap = new ConcurrentHashMap<MessageClientType, Client>();
                    clientTypeClientMap.put(messageClientType, registeredClient);
                    this.messageClientOnThisServerMap.put(loginId, clientTypeClientMap);
                }
            } else {
                throw new ClientNotRegisterException(linkedHost, linkedPort);
            }
        } else {
            throw new ClientNotRegisterException(linkedHost, linkedPort);
        }
    }

    @Override
    public void registerClient(MessageClientBasic messageClientBasic, ServerBasic serverBasic) throws ServerNotRegisterException, MessageClientRegisteredException {
        logger.debug("register other server message client:" + messageClientBasic.getLoginId() + "[" + serverBasic.getNodeId() + "]");
        if (!this.serverClientMap.containsKey(serverBasic.getNodeId())) {
            throw new ServerNotRegisterException(serverBasic.getNodeId());
        }
        if (!this.messageClientOnOtherServerMap.containsKey(serverBasic.getNodeId())) {
            throw new ServerNotRegisterException(serverBasic.getNodeId());
        }

        Map<String, Map<MessageClientType, MessageClientBasic>> loginIdMessageClientMap = this.messageClientOnOtherServerMap.get(serverBasic.getNodeId());

        if (loginIdMessageClientMap.containsKey(messageClientBasic.getLoginId())) {
            Map<MessageClientType, MessageClientBasic> clientTypeMessageClientBasicMap = loginIdMessageClientMap.get(messageClientBasic.getLoginId());
            if (clientTypeMessageClientBasicMap.containsKey(messageClientBasic.getMessageClientType())) {
                throw new MessageClientRegisteredException(messageClientBasic.getMessageClientType(), messageClientBasic.getLoginId());
            } else {
                clientTypeMessageClientBasicMap.put(messageClientBasic.getMessageClientType(), messageClientBasic);
            }
        } else {
            Map<MessageClientType, MessageClientBasic> clientTypeMessageClientBasicMap = new ConcurrentHashMap<MessageClientType, MessageClientBasic>();
            clientTypeMessageClientBasicMap.put(messageClientBasic.getMessageClientType(), messageClientBasic);
            loginIdMessageClientMap.put(messageClientBasic.getLoginId(), clientTypeMessageClientBasicMap);
        }
    }

    @Override
    public void registerClient(ServerBasic serverBasic, String linkedHost, int linkedPort) throws ServerRegisteredException, ClientNotRegisterException {
        logger.debug("register server client:" + serverBasic.getNodeId() + "[" + linkedHost + ":" + linkedPort + "]");
        if (this.serverClientMap.containsKey(serverBasic.getNodeId())) {
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
                this.messageClientOnOtherServerMap.put(serverBasic.getNodeId(), new ConcurrentHashMap<String, Map<MessageClientType, MessageClientBasic>>());
            } else {
                throw new ClientNotRegisterException(linkedHost, linkedPort);
            }
        } else {
            throw new ClientNotRegisterException(linkedHost, linkedPort);
        }
    }

    @Override
    public void removeClient(ChannelHandlerContext context) throws ServerNotRegisterException, ClientNotRegisterException {
        logger.debug("before=====================");
        dump();
        Client client = new Client(context);
        removeClient(client.getRemoteHost(), client.getRemotePort());
        logger.debug("after=====================");
        dump();
    }

    @Override
    public void removeClient(String host, int port) throws ServerNotRegisterException, ClientNotRegisterException {
        logger.debug("remove registered client[" + host + ":" + port + "]");
        logger.debug("before=====================");
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
        logger.debug("after=====================");
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
    public Collection<Client> getClients(String loginId) {
        Collection<Client> clients = new ArrayList<Client>();
        //get message client who linked on this server.
        if (this.messageClientOnThisServerMap.containsKey(loginId)) {
            clients.addAll(this.messageClientOnThisServerMap.get(loginId).values());
        }
        //get message clients who linked on other server.
        Iterator<String> serverMessageClientKeyIterator = this.messageClientOnOtherServerMap.keySet().iterator();
        while (serverMessageClientKeyIterator.hasNext()) {
            String serverNodeId = serverMessageClientKeyIterator.next();
            Map<String, Map<MessageClientType, MessageClientBasic>> loginIdMessageClientBasicMap = this.messageClientOnOtherServerMap.get(serverNodeId);
            if (loginIdMessageClientBasicMap.containsKey(loginId)) {
                if (this.serverClientMap.containsKey(serverNodeId)) {
                    clients.add(this.serverClientMap.get(serverNodeId));
                }
            }
        }
        return clients;
    }


    @Override
    public Client getClient(MessageClientType messageClientType, String loginId) throws ClientNotFoundException {
        if (this.messageClientOnThisServerMap.containsKey(loginId)) {
            Map<MessageClientType, Client> clientTypeMessageClientMap = this.messageClientOnThisServerMap.get(loginId);
            if (clientTypeMessageClientMap.containsKey(messageClientType)) {
                return clientTypeMessageClientMap.get(messageClientType);
            }
        }
        Iterator<String> serverMessageClientKeyIterator = this.messageClientOnOtherServerMap.keySet().iterator();
        while (serverMessageClientKeyIterator.hasNext()) {
            String serverNodeId = serverMessageClientKeyIterator.next();
            Map<String, Map<MessageClientType, MessageClientBasic>> loginIdMessageClientMap = this.messageClientOnOtherServerMap.get(serverNodeId);
            if (loginIdMessageClientMap.containsKey(loginId)) {
                Map<MessageClientType, MessageClientBasic> clientTypeMessageClientBasicMap = loginIdMessageClientMap.get(loginId);
                if (clientTypeMessageClientBasicMap.containsKey(messageClientType)) {
                    if (this.serverClientMap.containsKey(serverNodeId)) {
                        return this.serverClientMap.get(serverNodeId);
                    }
                }
            }
        }
        throw new ClientNotFoundException(loginId);
    }

    @Override
    public Collection<Client> getClients(ServerType serverType) {
        List<Client> clients = new ArrayList<Client>();
        for (String key : this.serverClientMap.keySet()) {
            ServerClient serverClient = (ServerClient) this.serverClientMap.get(key);
            ServerBasic serverBasic = serverClient.getServerBasic();
            if (serverBasic.getServerType() == serverType) {
                clients.add(this.serverClientMap.get(key));
            }
        }
        return clients;
    }

    @Override
    public Collection<Client> getAllMessageClients() {
        Collection<Client> messageClients = new ArrayList<Client>();
        Iterator<String> messageClientIterator = this.messageClientOnOtherServerMap.keySet().iterator();
        while (messageClientIterator.hasNext()) {
            String loginId = messageClientIterator.next();
            messageClients.addAll(this.messageClientOnThisServerMap.get(loginId).values());
        }
        return messageClients;
    }

    @Override
    public Collection<Client> getAllServerClients() {
        return this.serverClientMap.values();
    }

    private void removeServerClientMap(Client removeClient) throws ServerNotRegisterException {
        String deletingServerNode = "";
        for (String key : this.serverClientMap.keySet()) {
            Client currentClient = this.serverClientMap.get(key);
            if (currentClient.equals(removeClient)) {
                deletingServerNode = key;
                break;
            }
        }
        if (!deletingServerNode.equals("")) {
            removeMessageClientOnThisServer(deletingServerNode);
            this.serverClientMap.remove(deletingServerNode);
        }
    }

    private void removeMessageClientOnThisServer(String serverNode) {
        if (this.messageClientOnOtherServerMap.containsKey(serverNode)) {
            this.messageClientOnOtherServerMap.remove(serverNode);
        }
    }


    private void removeMessageClientMap(Client removeClient) {
        logger.debug("removing client => " + removeClient.toString());
        if (removeClient instanceof MessageClient) {
            MessageClient messageClient = (MessageClient) removeClient;
            if (this.messageClientOnThisServerMap.containsKey(messageClient.getLoginId())) {
                Map<MessageClientType, Client> clientMap = this.messageClientOnThisServerMap.get(messageClient.getLoginId());
                if (clientMap.containsKey(messageClient)) {
                    clientMap.remove(messageClient.getMessageClientType());
                    logger.debug(messageClient.toString() + " removed!!!!!!!!!");
                } else {
                    logger.debug(messageClient.getLoginId() + " : " + messageClient.getMessageClientType() + "not found");
                }

                if (CollectionUtils.isEmpty(clientMap)) {
                    this.messageClientOnThisServerMap.remove(messageClient.getLoginId());
                }
            } else {
                logger.debug(messageClient.getLoginId() + " not found");
            }

        }
    }
}
