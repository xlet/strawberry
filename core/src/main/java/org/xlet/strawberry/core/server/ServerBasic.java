package org.xlet.strawberry.core.server;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 下午4:34.
 * Summary: 消息服务基础信息.
 */
public class ServerBasic {

    private int port;
    private boolean start;
    private long startDateTime;
    private String nodeId;
    private ServerType serverType;
    private String lanHost;
    private String outerHost;

    private ServerBasic() {
    }

    /**
     * constructor.
     *
     * @param serverType server type.
     * @param port       port.
     */
    public ServerBasic(ServerType serverType, String lanHost, String outerHost, int port) {
        this.serverType = serverType;
        this.port = port;
        this.lanHost = lanHost;
        this.outerHost = outerHost;
        this.nodeId = lanHost + ":" + port;
    }

    /**
     * get local lanHost.
     *
     * @return local ip address.
     */
    public String getLanHost() {
        return lanHost;
    }

    /**
     * set local lanHost.
     *
     * @param lanHost lanHost.
     */
    public void setLanHost(String lanHost) {
        this.lanHost = lanHost;
    }

    /**
     * get outer host.
     *
     * @return outer host.
     */
    public String getOuterHost() {
        return outerHost;
    }

    /**
     * set outer host.
     *
     * @param outerHost outer host.
     */
    public void setOuterHost(String outerHost) {
        this.outerHost = outerHost;
    }

    /**
     * 获取服务监听端口号.
     *
     * @return 服务监听端口号.
     */
    public int getPort() {
        return port;
    }

    /**
     * 设置服务监听端口号.
     *
     * @param port 服务监听端口号.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * 获取服务是否启动.
     *
     * @return 启动:true.
     */
    public boolean isStart() {
        return start;
    }

    /**
     * 设置服务是否启动.
     *
     * @param start 启动:true.
     */
    public void setStart(boolean start) {
        this.start = start;
    }

    /**
     * 获取服务启动时间.
     *
     * @return 服务启动时间.
     */
    public long getStartDateTime() {
        return startDateTime;
    }

    /**
     * 设置服务启动时间.
     *
     * @param startDateTime 服务启动时间.
     */
    public void setStartDateTime(long startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * 获取服务节点标识.
     *
     * @return 服务节点标识.
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * 设置服务节点标识.
     *
     * @param nodeId 服务节点标识.
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * get server type.
     *
     * @return server type.
     */
    public ServerType getServerType() {
        return serverType;
    }

    /**
     * set server type.
     *
     * @param serverType server type.
     */
    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }
}
