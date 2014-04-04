package cn.w.im.server;

import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.ServerType;

import java.util.Date;
import java.util.UUID;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午9:56.
 * Summary: 抽象服务信息类.
 */
public abstract class AbstractServer {

    private ServerBasic serverBasic;

    private ClientCacheProvider clientCacheProvider;
    private SendMessageProvider sendMessageProvider;
    private RespondProvider respondProvider;
    private boolean init = false;

    /**
     * 构造函数.
     *
     * @param serverType 服务类型.
     */
    public AbstractServer(ServerType serverType) {
        this.serverBasic = new ServerBasic();
        this.serverBasic.setServerType(serverType);
    }

    /**
     * 初始化.
     *
     * @param host 服务绑定ip.
     * @param port 服务监听端口.
     * @return AbstractServer.
     */
    public AbstractServer init(String host, int port) {
        if (!init) {
            this.init = true;
            this.setHost(host);
            this.setPort(port);
            this.serverBasic.setNodeId(host + ":" + port);
        }
        return this;
    }

    /**
     * 服务启动时调用.
     */
    public void start() {
        if (!this.serverBasic.isStart()) {
            this.serverBasic.setStart(true);
            this.serverBasic.setStartDateTime(new Date().getTime());
            this.clientCacheProvider = new DefaultClientCacheProvider();
            this.respondProvider = new DefaultRespondProvider();
            this.sendMessageProvider = new DefaultSendMessageProvider(this.clientCacheProvider, this.respondProvider, this.serverBasic);
        }
    }

    /**
     * 服务关闭时调用.
     */
    public void stop() {
        if (this.serverBasic.isStart()) {
            this.serverBasic.setStart(false);
        }
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
     * 服务是否启动.
     *
     * @return true:启动.
     */
    public boolean isStart() {
        return this.serverBasic.isStart();
    }

    /**
     * 启动时间.
     *
     * @return true:启动.
     */
    public long getStartDate() {
        return this.serverBasic.getStartDateTime();
    }

    /**
     * 获取绑定host.
     *
     * @return host.
     */
    public String getHost() {
        return this.serverBasic.getHost();
    }

    /**
     * 获取监听端口号.
     *
     * @return 端口号.
     */
    public int getPort() {
        return this.serverBasic.getPort();
    }

    /**
     * 获取服务标识.
     *
     * @return 标识.
     */
    public String getNodeId() {
        return this.serverBasic.getNodeId();
    }

    /**
     * 获取服务基本信息.
     *
     * @return 获取服务基本信息.
     */
    public ServerBasic getServerBasic() {
        return this.serverBasic;
    }

    /**
     * 获取服务类型.
     *
     * @return 服务类型.
     */
    public ServerType getServerType() {
        return this.serverBasic.getServerType();
    }

    /**
     * 设置绑定host.
     *
     * @param host host.
     */
    protected void setHost(String host) {
        this.serverBasic.setHost(host);
    }

    /**
     * 设置监听端口.
     *
     * @param port 端口号.
     */
    protected void setPort(int port) {
        this.serverBasic.setPort(port);
    }

    /**
     * get client cache provider.
     *
     * @return client cache provider.
     */
    public ClientCacheProvider clientCacheProvider() {
        return clientCacheProvider;
    }

    /**
     * get send message provider.
     *
     * @return send message provider.
     */
    public SendMessageProvider sendMessageProvider() {
        return sendMessageProvider;
    }

    /**
     * get respond provider.
     *
     * @return respond provider.
     */
    public RespondProvider respondProvider() {
        return respondProvider;
    }
}
