package cn.w.im.domains.server;

import cn.w.im.utils.netty.IpAddressProvider;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午3:16.
 * Summary: 登录服务信息.
 */
public class LoginServer {

    private static LoginServer currentLoginServer;

    /**
     * 单例获取LoginServer.
     * @return 登陆服务信息.
     * @throws UnknownHostException 未知主机异常.
     */
    public static LoginServer current() throws UnknownHostException {
        if (currentLoginServer == null) {
            currentLoginServer = new LoginServer();
        }
        return currentLoginServer;
    }

    private final int serverPort = 18000;

    private Date startDate;

    private boolean start;

    private String localIpAddress;

    private List<MessageServer> messageServers;

    /**
     * 获取服务监听端口.
     * @return 监听端口.
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * 获取服务开始时间.
     * @return 服务开始时间.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 获取服务是否启动.
     * @return 启动：true  未启动:false.
     */
    public boolean isStart() {
        return start;
    }

    /**
     * 获取本地ip地址.
     * @return ip地址.
     */
    public String getLocalIpAddress() {
        return localIpAddress;
    }

    /**
     * 构造函数.
     * @throws UnknownHostException 未知主机异常.
     */
    public LoginServer() throws UnknownHostException {
        this.localIpAddress = IpAddressProvider.getLocalIpAddress();
        this.messageServers = new CopyOnWriteArrayList<MessageServer>();
    }

    /**
     * 启动服务时调用.
     */
    public void start() {
        this.start = true;
        this.startDate = new Date();
    }
}
