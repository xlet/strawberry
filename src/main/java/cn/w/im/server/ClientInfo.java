package cn.w.im.server;

import io.netty.channel.ChannelHandlerContext;

/**
 * Creator: JackieHan
 * DateTime: 13-12-17 下午3:59
 */
public class ClientInfo {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String ipAddress;

    private int port;

    private ChannelHandlerContext context;

    public String getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ChannelHandlerContext getContext() {
        return context;
    }

    public void setContext(ChannelHandlerContext context) {
        this.context = context;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;

    }

    public ClientInfo(String ipAddress,int port,ChannelHandlerContext context,String id){
        this.ipAddress=ipAddress;
        this.port=port;
        this.context=context;
        this.id=id;
    }
}
