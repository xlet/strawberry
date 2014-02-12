package cn.w.im.domains.client;

import cn.w.im.domains.ServerBasic;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午3:04.
 * Summary: 服务做为客户端.
 */
public abstract class ServerClient extends Client {

    private ServerBasic serverBasic;

    /**
     * 构造函数.
     * @param serverBasic 服务基础信息.
     * @param clientType 客户端类型.
     */
    public ServerClient(ServerBasic serverBasic, ClientType clientType) {
        super(clientType);
        this.serverBasic = serverBasic;
    }

    /**
     * 获取消息服务绑定ip.
     * @return ip.
     */
    public String getserverhost() {
        return serverBasic.getHost();
    }

    /**
     * 消息服务监听端口号.
     * @return 端口.
     */
    public int getserverport() {
        return serverBasic.getPort();
    }

    /**
     * 获取服务是否启动.
     * @return true:启动.
     */
    public boolean isStart() {
        return serverBasic.isStart();
    }

    /**
     * 获取启动时间.
     * @return 启动时间.
     */
    public Date getStartDate() {
        return serverBasic.getStartDateTime();
    }

    /**
     * 获取服务标识.
     * @return 标识.
     */
    public String getNodeId() {
        return serverBasic.getNodeId();
    }
}
