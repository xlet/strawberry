package org.xlet.strawberry.netty;

import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-31 上午9:31.
 * Summary: ip地址相关.
 */
public class IpAddressProvider {

    /**
     * 获取远程ip地址.
     *
     * @param ctx ChanncelHandlerContext.
     * @return ip地址.
     */
    public static String getRemoteIpAddress(ChannelHandlerContext ctx) {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().isSiteLocalAddress();
        return remoteAddress.getHostString();
    }

    /**
     * 获取远程端口号.
     *
     * @param ctx ChannelHandlerContext.
     * @return 端口号.
     */
    public static int getRemotePort(ChannelHandlerContext ctx) {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        return remoteAddress.getPort();
    }
}
