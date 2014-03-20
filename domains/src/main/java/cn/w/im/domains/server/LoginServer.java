package cn.w.im.domains.server;

import cn.w.im.domains.OtherServerBasic;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.SourceType;
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

    private boolean init = false, connectedBusServer = false;

    private int busPort;

    private String busHost;

    private List<OtherServerBasic> startedMessageServers;

    /**
     * record message server linked clients count.
     * <p/>
     * String: message server node id.
     * Integer: linked this server clients count.
     */
    private ConcurrentHashMap<String, Integer> messageServerClientCount;

    private ChannelHandlerContext forwardContext;

    /**
     * started other login server.
     * <p/>
     * if a client login on this server and send a message to these started login server.
     */
    private List<OtherServerBasic> startedOtherLoginServers;

    /**
     * 获取注册到messageBusServer的线程是否启动.
     *
     * @return true:启动.
     */
    public boolean isConnectedBusServer() {
        return connectedBusServer;
    }

    /**
     * 获取已注册的消息服务列表.
     *
     * @return 消息服务列表.
     */
    public List<OtherServerBasic> getStartedMessageServers() {
        return this.startedMessageServers;
    }

    /**
     * 获取消息总线服务监听端口.
     *
     * @return 端口号.
     */
    public int getBusPort() {
        return busPort;
    }

    /**
     * 获取消息总线host.
     *
     * @return host.
     */
    public String getBusHost() {
        return busHost;
    }

    /**
     * 获取转发连接Conext.
     *
     * @return Context.
     */
    public ChannelHandlerContext getForwardContext() {
        return forwardContext;
    }

    /**
     * 设置转发连接Context.
     *
     * @param forwardContext Context.
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

    /**
     * 构造函数.
     */
    private LoginServer() {
        super(ServerType.LoginServer);
        this.startedMessageServers = new CopyOnWriteArrayList<OtherServerBasic>();
        this.messageServerClientCount = new ConcurrentHashMap<String, Integer>();
        this.startedOtherLoginServers = new CopyOnWriteArrayList<OtherServerBasic>();
    }

    /**
     * 初始化.
     *
     * @param host    绑定Ip.
     * @param port    监听端口.
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
     * 连接到消息总线服务后调用.
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
     * 添加消息服务信息.
     *
     * @param startedMessageServer 消息服务信息.
     * @param sourceType           source type.
     */
    public void addStartedMessageServer(ServerBasic startedMessageServer, SourceType sourceType) {
        OtherServerBasic otherServerBasic = new OtherServerBasic(startedMessageServer, sourceType);
        this.startedMessageServers.add(otherServerBasic);
    }

    /**
     * 获取匹配消息服务信息.
     *
     * @return 消息服务信息.
     */
    public ServerBasic getMatchedMessageServer() {
        //todo jackie : abstract message server allocation arithmetic.
        int minClientCount = Integer.MAX_VALUE;
        String minClientCountKey = "";
        Iterator<String> keys = messageServerClientCount.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            int clientCount = messageServerClientCount.get(key);
            if (clientCount < minClientCount) {
                minClientCount = clientCount;
                minClientCountKey = key;
            }
        }

        ServerBasic matchedServer = null;

        Iterator<OtherServerBasic> servers = startedMessageServers.iterator();
        while (servers.hasNext()) {
            ServerBasic serverBasic = servers.next();
            if (minClientCountKey.equals(serverBasic.getNodeId())) {
                matchedServer = serverBasic;
            }
        }

        messageServerClientCount.replace(minClientCountKey, minClientCount + 1);
        return matchedServer;
    }

    /**
     * add started other login server basic.
     *
     * @param startedOtherLoginServer started other login server basic.
     * @param sourceType              source type.
     */
    public void addStartedOtherLoginServer(ServerBasic startedOtherLoginServer, SourceType sourceType) {
        OtherServerBasic otherServerBasic = new OtherServerBasic(startedOtherLoginServer, sourceType);
        this.startedOtherLoginServers.add(otherServerBasic);
    }

    /**
     * init messageServerClientCount with this ready message server.
     *
     * @param readyMessageServer ready message server basic.
     */
    public void messageServerReady(ServerBasic readyMessageServer) {
        if (!messageServerClientCount.containsKey(readyMessageServer.getNodeId())) {
            messageServerClientCount.put(readyMessageServer.getNodeId(), 0);
        }
    }
}
