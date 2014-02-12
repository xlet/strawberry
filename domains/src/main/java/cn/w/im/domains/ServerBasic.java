package cn.w.im.domains;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 下午4:34.
 * Summary: 消息服务基础信息.
 */
public class ServerBasic {

    private String host;
    private int port;
    private boolean start;
    private Date startDateTime;
    private String nodeId;

    /**
     * 获取服务绑定ip地址.
     * @return 服务绑定ip地址.
     */
    public String getHost() {
        return host;
    }

    /**
     * 设置服务绑定ip地址.
     * @param host 服务绑定ip地址.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * 获取服务监听端口号.
     * @return 服务监听端口号.
     */
    public int getPort() {
        return port;
    }

    /**
     * 设置服务监听端口号.
     * @param port 服务监听端口号.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * 获取服务是否启动.
     * @return 启动:true.
     */
    public boolean isStart() {
        return start;
    }

    /**
     * 设置服务是否启动.
     * @param start 启动:true.
     */
    public void setStart(boolean start) {
        this.start = start;
    }

    /**
     * 获取服务启动时间.
     * @return 服务启动时间.
     */
    public Date getStartDateTime() {
        return startDateTime;
    }

    /**
     * 设置服务启动时间.
     * @param startDateTime 服务启动时间.
     */
    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * 获取服务节点标识.
     * @return 服务节点标识.
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * 设置服务节点标识.
     * @param nodeId 服务节点标识.
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
