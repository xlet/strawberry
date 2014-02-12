package cn.w.im.domains.server;

import cn.w.im.domains.ServerBasic;
import io.netty.channel.ChannelHandlerContext;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午3:16.
 * Summary: 登录服务信息.
 */
public class LoginServer extends AbstractServer {

    private static LoginServer currentLoginServer;

    /**
     * 单例获取LoginServer.
     *
     * @return 登陆服务信息.
     */
    public synchronized static LoginServer current() {
        if (currentLoginServer == null) {
            currentLoginServer = new LoginServer();
        }
        return currentLoginServer;
    }

    private boolean init = false;

    private int busPort;

    private String busHost;

    private List<ServerBasic> messageServers;

    private ConcurrentHashMap<String, Integer> messageServerClients;

    private ChannelHandlerContext forwardContext;


    /**
     * 获取已注册的消息服务列表.
     *
     * @return 消息服务列表.
     */
    public List<ServerBasic> getMessageServers() {
        return this.messageServers;
    }

    /**
     * 获取消息总线服务监听端口.
     * @return 端口号.
     */
    public int getBusPort() {
        return busPort;
    }

    /**
     * 获取消息总线host.
     * @return host.
     */
    public String getBusHost() {
        return busHost;
    }

    /**
     * 获取转发连接Conext.
     * @return Context.
     */
    public ChannelHandlerContext getForwardContext() {
        return forwardContext;
    }

    /**
     * 设置转发连接Context.
     * @param forwardContext Context.
     */
    public void setForwardContext(ChannelHandlerContext forwardContext) {
        this.forwardContext = forwardContext;
    }

    /**
     * 构造函数.
     */
    private LoginServer() {
        super(ServerType.LoginServer);
        this.messageServers = new CopyOnWriteArrayList<ServerBasic>();
        this.messageServerClients = new ConcurrentHashMap<String, Integer>();
    }

    /**
     * 初始化.
     *
     * @param host 绑定Ip.
     * @param port 监听端口.
     * @param busHost 消息总线服务绑定ip.
     * @param busPort 消息总线服务监听端口.
     * @return 登陆服务信息实例.
     */
    public LoginServer init(String host, int port, String busHost, int busPort) {
        if (!init) {
            this.setPort(port);
            this.setHost(host);
            this.busHost = busHost;
            this.busPort = busPort;
            this.init = true;
        }
        return this;
    }

    /**
     * 添加消息服务信息.
     *
     * @param serverBasic 消息服务信息.
     */
    public void addMessageServer(ServerBasic serverBasic) {
        this.messageServers.add(serverBasic);
        messageServerClients.put(serverBasic.getNodeId(), 0);
    }

    /**
     * 获取匹配消息服务信息.
     *
     * @return 消息服务信息.
     */
    public ServerBasic getMessageServer() {
        int minClientCount = Integer.MAX_VALUE;
        String minClientCountKey = "";
        Iterator<String> keys = messageServerClients.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            int clientCount = messageServerClients.get(key);
            if (clientCount < minClientCount) {
                minClientCount = clientCount;
                minClientCountKey = key;
            }
        }

        ServerBasic matchedServer = null;

        Iterator<ServerBasic> servers = messageServers.iterator();
        while (servers.hasNext()) {
            ServerBasic serverBasic = servers.next();
            if (minClientCountKey.equals(serverBasic.getNodeId())) {
                matchedServer = serverBasic;
            }
        }

        messageServerClients.replace(minClientCountKey, minClientCount + 1);
        return matchedServer;
    }
}
