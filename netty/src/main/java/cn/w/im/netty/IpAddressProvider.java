package cn.w.im.netty;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

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
