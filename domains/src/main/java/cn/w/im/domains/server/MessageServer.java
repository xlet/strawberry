package cn.w.im.domains.server;

import cn.w.im.domains.LoginToken;
import cn.w.im.domains.OtherServerBasic;
import cn.w.im.domains.SourceType;
import cn.w.im.domains.client.MessageClient;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.MessageClientBasic;
import cn.w.im.domains.messages.ForwardMessage;
import cn.w.im.domains.messages.ReadyMessage;
import cn.w.im.domains.messages.RequestLinkedClientsMessage;
import cn.w.im.utils.netty.IpAddressProvider;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Creator: JackieHan.
 * DateTime: 13-11-15 下午1:59.
 * Summary:消息服务器信息.
 */
public class MessageServer extends AbstractServer {

    /**
     * 单例消息服务信息.
     */
    private static MessageServer currentServer;

    /**
     * 单例获取消息服务信息.
     *
     * @return 当前消息服务信息.
     */
    public synchronized static MessageServer current() {
        if (currentServer == null) {
            currentServer = new MessageServer();
        }
        return currentServer;
    }

    private boolean init = false, connectedBusServer = false;

    private String busHost;

    private int busPort;

    private ChannelHandlerContext forwardContext;

    private List<MessageClient> clients;

    private List<OtherServerBasic> startedOtherMessageServices;

    private List<OtherServerBasic> startedLoginServers;

    private Map<String, List<MessageClientBasic>> otherMessageServerClientMap;

    private List<LoginToken> tokens;

    /**
     * 构造函数.
     */
    private MessageServer() {
        super(ServerType.MessageServer);
        clients = new CopyOnWriteArrayList<MessageClient>();
        startedOtherMessageServices = new CopyOnWriteArrayList<OtherServerBasic>();
        startedLoginServers = new CopyOnWriteArrayList<OtherServerBasic>();
        tokens = new CopyOnWriteArrayList<LoginToken>();
        otherMessageServerClientMap = new ConcurrentHashMap<String, List<MessageClientBasic>>();
    }

    /**
     * 初始化.
     *
     * @param host    服务绑定ip.
     * @param port    服务监听端口.
     * @param busHost 登陆服务ip.
     * @param busPort 登陆服务监听端口.
     * @return MessageServer.
     */
    public MessageServer init(String host, int port, String busHost, int busPort) {
        if (!init) {
            this.init = true;
            this.setHost(host);
            this.setPort(port);
            this.busHost = busHost;
            this.busPort = busPort;
        }
        return this;
    }

    /**
     * 获取所有已连接客户端的基础信息.
     *
     * @return 所有已连接客户端的基础信息.
     */
    public List<MessageClientBasic> getLinkedClients() {
        List<MessageClientBasic> clientBasics = new ArrayList<MessageClientBasic>();
        Iterator<MessageClient> iterator = clients.iterator();
        while (iterator.hasNext()) {
            MessageClient client = iterator.next();
            MessageClientBasic clientBasic = new MessageClientBasic(client.getId(), client.getRemoteHost(), client.getRemotePort());
            clientBasics.add(clientBasic);
        }
        return clientBasics;
    }

    /**
     * 获取给定Id的客户端信息.
     *
     * @param id 客户端登陆Id.
     * @return 客户端信息.
     */
    public MessageClient getClient(String id) {
        Iterator<MessageClient> iterator = clients.iterator();
        while (iterator.hasNext()) {
            MessageClient messageClient = iterator.next();
            if (messageClient.getId().equals(id)) {
                return messageClient;
            }
        }
        return null;
    }

    /**
     * 移除客户端注册.
     *
     * @param ip   客户端ip地址.
     * @param port 客户端端口.
     */
    public void removeClient(String ip, int port) {
        Iterator<MessageClient> iterator = clients.iterator();
        MessageClient removeClient = null;
        while (iterator.hasNext()) {
            MessageClient messageClient = iterator.next();
            String remoteIp = IpAddressProvider.getRemoteIpAddress(messageClient.getContext());
            int remotePort = IpAddressProvider.getRemotePort(messageClient.getContext());
            if (remoteIp.equals(ip) && remotePort == port) {
                removeClient = messageClient;
                break;
            }
        }
        clients.remove(removeClient);
    }

    /**
     * 移除客户端注册.
     *
     * @param id 客户端登陆id.
     */
    public void removeClient(String id) {
        MessageClient removeInfo = getClient(id);
        clients.remove(removeInfo);
    }

    /**
     * 添加连接客户端.
     *
     * @param client 客户端信息.
     */
    public void addClient(MessageClient client) {
        this.clients.add(client);
    }

    /**
     * 添加已启动的服务.
     *
     * @param startedOtherMessageServerBasic 已启动的服务信息.
     * @param sourceType                     other server basic source type.
     */
    public void addStartedOtherMessageServer(ServerBasic startedOtherMessageServerBasic, SourceType sourceType) {
        OtherServerBasic otherServerBasic = new OtherServerBasic(startedOtherMessageServerBasic, sourceType);
        this.startedOtherMessageServices.add(otherServerBasic);
    }

    /**
     * 添加已启动的服务集合.
     *
     * @param startedOtherMessageServers 已启动的服务信息集合.
     * @param sourceType                 other server basic source type.
     */
    public void addStartedOtherMessageServers(List<ServerBasic> startedOtherMessageServers, SourceType sourceType) {
        for (ServerBasic startedOtherMessageServer : startedOtherMessageServers) {
            addStartedOtherMessageServer(startedOtherMessageServer, sourceType);
        }
    }

    /**
     * add started login servers basic.
     *
     * @param startedLoginServers started login servers basic.
     * @param sourceType          other server basic source type.
     */
    public void addStartedLoginServers(List<ServerBasic> startedLoginServers, SourceType sourceType) {
        for (ServerBasic startedLoginServer : startedLoginServers) {
            addStartedLoginServer(startedLoginServer, sourceType);
        }
    }

    /**
     * add one started login server basic.
     *
     * @param startedLoginServer started login server basic.
     * @param sourceType         other server basic source type.
     */
    public void addStartedLoginServer(ServerBasic startedLoginServer, SourceType sourceType) {
        OtherServerBasic otherServerBasic = new OtherServerBasic(startedLoginServer, sourceType);
        this.startedLoginServers.add(otherServerBasic);
    }

    /**
     * get all started login servers basic.
     *
     * @return all started login servers.
     */
    public List<OtherServerBasic> getStartedLoginServers() {
        return this.startedLoginServers;
    }

    /**
     * 获取其他消息服务信息.
     *
     * @param loginId 登陆Id.
     * @return 服务信息.
     */
    public ServerBasic getOtherServer(String loginId) {
        String matchedServerNodeId = getOtherServerNodeId(loginId);
        if (matchedServerNodeId.isEmpty()) {
            return null;
        }

        Iterator<OtherServerBasic> serverIterator = startedOtherMessageServices.iterator();
        while (serverIterator.hasNext()) {
            ServerBasic serverBasic = serverIterator.next();
            if (serverBasic.getNodeId().equals(matchedServerNodeId)) {
                return serverBasic;
            }
        }
        return null;
    }

    private String getOtherServerNodeId(String loginId) {
        Iterator<String> keyIterator = otherMessageServerClientMap.keySet().iterator();
        while (keyIterator.hasNext()) {
            String nodeId = keyIterator.next();
            List<MessageClientBasic> messageClients = otherMessageServerClientMap.get(nodeId);
            Iterator<MessageClientBasic> clientIterator = messageClients.iterator();
            while (clientIterator.hasNext()) {
                MessageClientBasic clientBasic = clientIterator.next();
                if (clientBasic.getLoginId().equals(loginId)) {
                    return nodeId;
                }
            }
        }
        return "";
    }

    /**
     * 添加其他消息服务客户端.
     *
     * @param otherServer 其他消息服务.
     * @param clients     连接在其他消息服务上的客户端.
     */
    public void addOtherServerClients(ServerBasic otherServer, List<MessageClientBasic> clients) {
        if (otherMessageServerClientMap.containsKey(otherServer.getNodeId())) {
            List<MessageClientBasic> existedClients = otherMessageServerClientMap.get(otherServer.getNodeId());
            for (MessageClientBasic client : clients) {
                existedClients.add(client);
            }
        } else {
            otherMessageServerClientMap.put(otherServer.getNodeId(), clients);
        }
    }

    /**
     * send RequestLinkedClientsMessage to other started message server.
     */
    public void requestLinkedClients() {
        for (ServerBasic messageServerBasic : this.startedOtherMessageServices) {
            RequestLinkedClientsMessage requestMessage = new RequestLinkedClientsMessage(MessageServer.current().getServerBasic());
            ForwardMessage forwardMessage = new ForwardMessage(this.getServerBasic(), messageServerBasic, requestMessage);
            this.getForwardContext().writeAndFlush(forwardMessage);
        }
    }

    /**
     * check all started message server has responded linked clients.
     *
     * @return true:finished.
     */
    public boolean finishedRequestLinkedClients() {
        Iterator<OtherServerBasic> startedOtherMessageServerIterator = this.startedOtherMessageServices.iterator();
        while (startedOtherMessageServerIterator.hasNext()) {
            OtherServerBasic startedOtherMessageServer = startedOtherMessageServerIterator.next();
            if ((startedOtherMessageServer.getSourceType() == SourceType.Pull) && (!otherMessageServerClientMap.containsKey(startedOtherMessageServer.getNodeId()))) {
                return false;
            }
        }
        return true;
    }


    /**
     * send ready message to login server.
     */
    public void ready() {
        ReadyMessage readyMessage = new ReadyMessage();
        readyMessage.setMessageServer(MessageServer.current().getServerBasic());
        Iterator<OtherServerBasic> loginServerIterator = this.getStartedLoginServers().iterator();
        while (loginServerIterator.hasNext()) {
            ServerBasic loginServerBasic = loginServerIterator.next();
            ForwardMessage forwardMessage = new ForwardMessage(this.getServerBasic(), loginServerBasic, readyMessage);
            this.getForwardContext().writeAndFlush(forwardMessage);
        }
    }

    /**
     * 添加登陆token.
     *
     * @param token token信息.
     */
    public void addToken(LoginToken token) {
        this.tokens.add(token);
    }

    /**
     * 连接到消息总线后调用.
     */
    public void connectedBusServer() {
        this.connectedBusServer = true;
    }

    /**
     * 服务关闭时调用.
     */
    public void disconnectedBusServer() {
        this.connectedBusServer = false;
    }

    /**
     * 获取是否连接到消息总线.
     *
     * @return true:已连接.
     */
    public boolean isConnectedBusServer() {
        return connectedBusServer;
    }

    /**
     * 获取消息总线服务绑定ip.
     *
     * @return ip.
     */
    public String getBusHost() {
        return busHost;
    }

    /**
     * 获取消息总线服务监听端口.
     *
     * @return 端口.
     */
    public int getBusPort() {
        return busPort;
    }

    /**
     * 获取转发Context.
     *
     * @return 转发Context.
     */
    public ChannelHandlerContext getForwardContext() {
        return forwardContext;
    }

    /**
     * 设置转发Context.
     *
     * @param forwardContext 转发Context.
     */
    public void setForwardContext(ChannelHandlerContext forwardContext) {
        this.forwardContext = forwardContext;
    }

    /**
     * 获取服务是否初始化.
     *
     * @return true:已经初始化
     */
    public boolean isInit() {
        return init;
    }
}
