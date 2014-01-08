package cn.w.im.domains;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Creator: JackieHan.
 * DateTime: 13-11-15 下午1:59.
 * Summary:当前服务器信息.
 */
public class ServerInfo {

    /**
     * 单例ServerInfo.
     */
    private static ServerInfo currentServer;

    /**
     * 单例获取ServerInfo.
     * @return 当前ServerInfo.
     * @throws UnknownHostException 未知注意异常.
     */
    public static ServerInfo current() throws UnknownHostException {
        if (currentServer == null) {
            currentServer = new ServerInfo();
        }
        return currentServer;
    }

    /**
     * 客户端连接监听端口.
     */
    private final int serverPort = 16000;
    /**
     * 服务器与服务器的监听端口.
     */
    private final int serverToServerPort = 17000;

    /**
     * 开始运行时间.
     */
    private Date startDate;

    /**
     * 是否启动.
     */
    private boolean isStart;

    /**
     * 本地ip地址.
     */
    private String localIpAddress;

    /**
     * 此服务器的标识.
     */
    private String nodeId;

    /**
     * 连接到当前服务器的客户端信息集合.
     */
    private List<ClientInfo> clients;

    /**
     * 构造函数.
     *
     * @throws UnknownHostException 位置主机异常.
     */
    private ServerInfo() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        this.localIpAddress = address.getHostAddress().toString();

        this.nodeId = UUID.randomUUID().toString();

        clients = new CopyOnWriteArrayList<ClientInfo>();
    }


    /**
     * 获取客户端连接监听端口.
     *
     * @return 端口号.
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * 获取服务器与服务器连接监听端口.
     *
     * @return 端口号.
     */
    public int getServerToServerPort() {
        return serverToServerPort;
    }

    /**
     * 获取服务器启动时间.
     *
     * @return 启动时间.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置服务器启动时间.
     *
     * @param startDate 启动时间.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 服务器是否启动.
     *
     * @return 启动:true 未启动:false.
     */
    public boolean isStart() {
        return isStart;
    }

    /**
     * 设置启动标识.
     *
     * @param start 是否启动.
     */
    public void setStart(boolean start) {
        isStart = start;
    }

    /**
     * 获取本地Ip地址.
     *
     * @return ip地址.
     */
    public String getLocalIpAddress() {
        return localIpAddress;
    }

    /**
     * 获取服务器的标识.
     *
     * @return 服务器标识.
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * 获取给定Id的客户端信息.
     *
     * @param id 客户端登陆Id.
     * @return 客户端信息.
     */
    public ClientInfo getClient(String id) {
        Iterator<ClientInfo> iterator = clients.iterator();
        while (iterator.hasNext()) {
            ClientInfo clientInfo = iterator.next();
            if (clientInfo.getId().equals(id)) {
                return clientInfo;
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
        Iterator<ClientInfo> iterator = clients.iterator();
        ClientInfo removeClient = null;
        while (iterator.hasNext()) {
            ClientInfo clientInfo = iterator.next();
            if (clientInfo.getContext().getClientIpAddress().equals(ip)
                    && clientInfo.getContext().getClientPort() == port) {
                removeClient = clientInfo;
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
        ClientInfo removeInfo = getClient(id);
        clients.remove(removeInfo);
    }

    /**
     * 添加连接客户端.
     *
     * @param client 客户端信息.
     */
    public void addClient(ClientInfo client) {
        this.clients.add(client);
    }
}
