package cn.w.im.domains.client;

import cn.w.im.utils.netty.IpAddressProvider;
import io.netty.channel.ChannelHandlerContext;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午2:51.
 * Summary: 客户端信息.
 */
public abstract class Client {

    private ClientType clientType;

    private ChannelHandlerContext context;

    /**
     * 构造函数.
     * @param clientType 客户端类型.
     */
    public Client(ClientType clientType) {
        this.clientType = clientType;
    }

    /**
     * 获取客户端类型.
     * @return 客户端类型.
     */
    public ClientType getClientType() {
        return clientType;
    }

    /**
     * 获取当前channelContext.
     * @return channelContext.
     */
    public ChannelHandlerContext getContext() {
        return context;
    }

    /**
     * 设置当前channelContext.
     * @param context channelContext.
     */
    public void setContext(ChannelHandlerContext context) {
        this.context = context;
    }

    /**
     * 获取远程host.
     * @return host.
     */
    public String getRemoteHost() {
        return IpAddressProvider.getRemoteIpAddress(this.getContext());
    }

    /**
     * 获取远程端口.
     * @return 端口.
     */
    public int getRemotePort() {
        return IpAddressProvider.getRemotePort(this.getContext());
    }
}