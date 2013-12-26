package cn.w.im.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Creator: JackieHan
 * DateTime: 13-11-15 下午1:59
 */
public class ServerInfo {

    private final int serverPort = 16000;
    private final int serverToServerPort = 17000;

    private Date startDate;
    private boolean isStart;
    private String localIpAddress;
    private String nodeId;

    private List<ClientInfo> clients;

    public ServerInfo() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        setLocalIpAddress(address.getHostAddress().toString());

        setNodeId(UUID.randomUUID().toString());

        clients = new CopyOnWriteArrayList<ClientInfo>();
    }


    public int getServerPort() {
        return serverPort;
    }

    public int getServerToServerPort() {
        return serverToServerPort;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public String getLocalIpAddress() {
        return localIpAddress;
    }

    public void setLocalIpAddress(String localIpAddress) {
        this.localIpAddress = localIpAddress;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public List<ClientInfo> getClients() {
        return clients;
    }

    /**
     * 获取给定Id的客户端信息
     * @param id 客户端登陆Id
     * @return 客户端信息
     */
    public ClientInfo getClient(String id){
        Iterator<ClientInfo> iterator = clients.iterator();
        while (iterator.hasNext()){
            ClientInfo clientInfo=iterator.next();
            if(clientInfo.getId().equals(id)){
                return clientInfo;
            }
        }
        return null;
    }

    /**
     * 移除客户端注册
     * @param ip 客户端ip地址
     * @param port 客户端端口
     */
    public void removeClient(String ip,int port){
        Iterator<ClientInfo> iterator=clients.iterator();
        ClientInfo removeClient=null;
        while (iterator.hasNext()){
            ClientInfo clientInfo=iterator.next();
            if(clientInfo.getIpAddress().equals(ip)&&clientInfo.getPort()==port){
                removeClient=clientInfo;
                break;
            }
        }
        clients.remove(removeClient);
    }

    /**
     * 移除客户端注册
     * @param id 客户端登陆id
     */
    public void removeClient(String id){
        ClientInfo removeInfo=getClient(id);
        clients.remove(removeInfo);
    }
}
