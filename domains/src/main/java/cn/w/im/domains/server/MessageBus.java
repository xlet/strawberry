package cn.w.im.domains.server;

import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.client.LoginServerClient;
import cn.w.im.domains.client.MessageServerClient;
import cn.w.im.domains.client.ServerClient;
import cn.w.im.domains.client.ClientType;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午9:41.
 * Summary: 消息总线信息.
 */
public class MessageBus extends AbstractServer {

    private static MessageBus currentMessageBus;

    /**
     * 获取单例消息总线信息.
     *
     * @return 消息总线信息.
     */
    public synchronized static MessageBus current() {
        if (currentMessageBus == null) {
            currentMessageBus = new MessageBus();
        }
        return currentMessageBus;
    }

    private boolean init = false;

    private List<Client> clients;

    private MessageBus() {
        super(ServerType.MessageBus);
        this.clients = new CopyOnWriteArrayList<Client>();
    }

    /**
     * 获取服务是否初始化.
     * @return true:已经初始化
     */
    public boolean isInit() {
        return init;
    }

    /**
     * 初始化服务信息.
     *
     * @param host 绑定ip.
     * @param port 监听端口.
     */
    public void init(String host, int port) {
        if (!init) {
            this.setHost(host);
            this.setPort(port);
            this.init = true;
        }
    }

    /**
     * 添加已启动消息服务.
     *
     * @param messageServer 消息服务信息.
     * @param context       当前连接Context.
     */
    public void addMessageServer(ServerBasic messageServer, ChannelHandlerContext context) {
        MessageServerClient client = new MessageServerClient(messageServer);
        client.setContext(context);
        clients.add(client);
    }

    /**
     * 添加已启动的登录服务.
     *
     * @param loginServer 登录服务信息.
     * @param context     当前连接Context.
     */
    public void addLoginServer(ServerBasic loginServer, ChannelHandlerContext context) {
        LoginServerClient client = new LoginServerClient(loginServer);
        client.setContext(context);
        clients.add(client);
    }

    /**
     * 获取客户端信息.
     *
     * @param serverBasic 服务信息.
     * @return 匹配的客户端.
     */
    public Client getClient(ServerBasic serverBasic) {
        Iterator<Client> iterator = clients.iterator();
        while (iterator.hasNext()) {
            Client client = iterator.next();
            String nodeId = "";
            switch (client.getClientType()) {
                case LoginServer:
                case MessageServer:
                    ServerClient serverClient = (ServerClient) client;
                    nodeId = serverClient.getNodeId();
                    break;
            }
            if (serverBasic.getNodeId().equals(nodeId)) {
                return client;
            }
        }
        return null;
    }

    /**
     * 获取客户端信息.
     *
     * @param host 客户端host.
     * @param port 客户端端口号.
     * @return 客户端信息.
     */
    public Client getClient(String host, int port) {
        Iterator<Client> iterator = clients.iterator();
        Client matchedClient = null;
        while (iterator.hasNext()) {
            Client client = iterator.next();
            if ((client.getRemoteHost().equals(host)) && (client.getRemotePort() == port)) {
                matchedClient = client;
            }
        }
        return matchedClient;
    }

    /**
     * 移除客户端.
     *
     * @param host 客户端host.
     * @param port 客户端端口号.
     */
    public void removeClient(String host, int port) {
        Client deleteClient = getClient(host, port);
        if (deleteClient != null) {
            clients.remove(deleteClient);
        }
    }

    /**
     * 获取已注册的消息服务信息.
     *
     * @return 消息服务信息列表.
     */
    public List<Client> getRegisteredMessageServers() {
        return this.getClients(ClientType.MessageServer);
    }

    /**
     * 获取已注册的登录服务信息.
     *
     * @return 登陆服务信息列表.
     */
    public List<Client> getRegisteredLoginServers() {
        return this.getClients(ClientType.LoginServer);
    }

    private List<Client> getClients(ClientType clientType) {
        List<Client> matchedClients = new ArrayList<Client>();
        Iterator<Client> iterator = this.clients.iterator();
        while (iterator.hasNext()) {
            Client client = iterator.next();
            if (client.getClientType() == clientType) {
                matchedClients.add(client);
            }
        }
        return matchedClients;
    }
}
