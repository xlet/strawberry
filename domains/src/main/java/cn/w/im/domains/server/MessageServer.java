package cn.w.im.domains.server;

import cn.w.im.domains.client.MessageClient;
import cn.w.im.domains.ServerBasic;
import cn.w.im.utils.netty.IpAddressProvider;

import java.util.Iterator;
import java.util.List;
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

    private boolean init = false;

    private String busHost;

    private int busPort;

    private List<MessageClient> clients;

    private List<ServerBasic> startedMessageServices;

    /**
     * 构造函数.
     */
    private MessageServer() {
        super(ServerType.MessageServer);
        clients = new CopyOnWriteArrayList<MessageClient>();
        startedMessageServices = new CopyOnWriteArrayList<ServerBasic>();
    }

    /**
     * 初始化.
     * @param host 服务绑定ip.
     * @param port 服务监听端口.
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
     * @param serverBasic 已启动的服务信息.
     */
    public void addServer(ServerBasic serverBasic) {
        this.startedMessageServices.add(serverBasic);
    }

    /**
     * 添加已启动的服务集合.
     * @param serverBasics 已启动的服务信息集合.
     */
    public void addServers(List<ServerBasic> serverBasics) {
        this.startedMessageServices.addAll(serverBasics);
    }

    /**
     * 获取已启动的服务遍历对象.
     * @return 服务遍历对象.
     */
    public Iterator<ServerBasic> getServerIterator() {
        return startedMessageServices.iterator();
    }

    /**
     * 获取消息总线服务绑定ip.
     * @return ip.
     */
    public String getBusHost() {
        return busHost;
    }

    /**
     * 获取消息总线服务监听端口.
     * @return 端口.
     */
    public int getBusPort() {
        return busPort;
    }
}
