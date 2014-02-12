package cn.w.im.domains.server;

import cn.w.im.domains.ServerBasic;

import java.util.Date;
import java.util.UUID;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 上午9:56.
 * Summary: 抽象服务信息类.
 */
public abstract class AbstractServer {

    private ServerBasic serverBasic;

    private ServerType serverType;

    /**
     * 构造函数.
     *
     * @param serverType 服务类型.
     */
    public AbstractServer(ServerType serverType) {
        this.serverType = serverType;
        serverBasic = new ServerBasic();
        serverBasic.setNodeId(UUID.randomUUID().toString().replace("-", ""));
    }

    /**
     * 服务启动时调用.
     */
    public void start() {
        if (!this.serverBasic.isStart()) {
            this.serverBasic.setStart(true);
            this.serverBasic.setStartDateTime(new Date());
        }
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
    public Date getStartDate() {
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
        return this.serverType;
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
}
