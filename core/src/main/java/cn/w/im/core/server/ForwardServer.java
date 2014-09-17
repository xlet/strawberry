package cn.w.im.core.server;

import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.forward.ForwardReadyMessage;
import cn.w.im.domains.messages.forward.ForwardRequestMessage;
import cn.w.im.domains.messages.forward.ForwardResponseMessage;
import cn.w.im.utils.netty.IpAddressProvider;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午3:07.
 * Summary: forward server.
 */
public class ForwardServer {

    private Logger logger;

    private String busHost, serverHost;
    private int busPort, serverPort;

    private boolean connectedError = false;

    /**
     * key host:port.
     */
    private Map<String, ChannelHandlerContext> contextMap;
    /**
     * key host:port.
     */
    private Map<String, ServerBasic> connectedServerMap;

    private List<String> serverKeys;

    /**
     * initialize server.
     *
     * @param busHost    message bus host.
     * @param busPort    message bus port.
     * @param serverHost connect server host.
     * @param serverPort connect server port.
     */
    public ForwardServer(String busHost, int busPort, String serverHost, int serverPort) {
        this.serverKeys = new ArrayList<String>();
        this.contextMap = new ConcurrentHashMap<String, ChannelHandlerContext>();
        this.connectedServerMap = new ConcurrentHashMap<String, ServerBasic>();
        logger = LoggerFactory.getLogger(this.getClass());
        init(busHost, busPort, serverHost, serverPort);
    }

    /**
     * initialize server.
     *
     * @param busHost    message bus host.
     * @param busPort    message bus port.
     * @param serverHost connect server host.
     * @param serverPort connect server port.
     */
    private void init(String busHost, int busPort, String serverHost, int serverPort) {
        this.busHost = busHost;
        this.busPort = busPort;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        String busKey = busHost + ":" + busPort;
        String serverKey = serverHost + ":" + serverPort;
        this.serverKeys.add(busKey);
        this.serverKeys.add(serverKey);
        logger.debug("core initialized.");
    }

    /**
     * get connected is error.
     *
     * @return true:error.
     */
    public boolean isConnectedError() {
        return this.connectedError;
    }

    /**
     * connected error.
     */
    public void connectedError() {
        logger.debug("connected error.");
        this.connectedError = true;
    }

    /**
     * connected one server.
     *
     * @param ctx current ChannelHandlerContext.
     */
    public void connected(ChannelHandlerContext ctx) {
        String key = getKey(ctx);
        logger.debug("connected core[" + key + "]");
        if (isServerKey(key)) {
            this.contextMap.put(key, ctx);
        }
    }

    private boolean isServerKey(String key) {
        for (String serverKey : this.serverKeys) {
            if (serverKey.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * all server connected.
     *
     * @return true:allConnected.
     */
    public boolean allConnected() {

        if (this.serverKeys.size() != this.contextMap.size()) {
            return false;
        }

        for (String serverKey : this.serverKeys) {
            if (!this.contextMap.containsKey(serverKey)) {
                return false;
            }
        }
        return true;
    }

    /**
     * get bus host.
     *
     * @return bus host.
     */
    public String getBusHost() {
        return busHost;
    }

    /**
     * get server host.
     *
     * @return server host.
     */
    public String getServerHost() {
        return serverHost;
    }

    /**
     * get bus port.
     *
     * @return bus port.
     */
    public int getBusPort() {
        return busPort;
    }

    /**
     * get server port.
     *
     * @return get server port.
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * request connected server basic.
     *
     * @param ctx current ChannelHandlerContext.
     */
    public void requestServerBasic(ChannelHandlerContext ctx) {
        logger.debug("request connected core basic.");
        ForwardRequestMessage requestMessage = new ForwardRequestMessage();
        ctx.writeAndFlush(requestMessage);
    }


    /**
     * receive connected server response message.
     *
     * @param responseMessage response message.
     * @param ctx             current ChannelHandlerContext.
     */
    public void receivedResponse(ForwardResponseMessage responseMessage, ChannelHandlerContext ctx) {
        String key = getKey(ctx);
        ServerBasic connectedServerBasic = responseMessage.getFromServer();
        if (this.isServerKey(key)) {
            this.connectedServerMap.put(key, connectedServerBasic);
        }
    }

    private String getKey(ChannelHandlerContext ctx) {
        String host = IpAddressProvider.getRemoteIpAddress(ctx);
        int port = IpAddressProvider.getRemotePort(ctx);
        return host + ":" + port;
    }

    /**
     * forward message.
     *
     * @param message message.
     * @param ctx     current ChannelHandlerContext.
     */
    public void forwardMessage(Message message, ChannelHandlerContext ctx) {
        //get remote ip:port
        String currentKey = getKey(ctx);
        for (String key : this.contextMap.keySet()) {
            // send to all channel except itself
            if (!key.equals(currentKey)) {
                this.contextMap.get(key).writeAndFlush(message);
            }
        }
    }

    /**
     * server and bus are all responded.
     *
     * @return true:all responded.
     */
    public boolean allResponded() {
        if (this.serverKeys.size() != this.connectedServerMap.size()) {
            return false;
        }

        for (String serverKey : this.serverKeys) {
            if (!this.connectedServerMap.containsKey(serverKey)) {
                return false;
            }
        }
        return true;
    }

    /**
     * forward server ready.
     *
     * @param ctx current ChannelHandlerContext.
     */
    public void ready(ChannelHandlerContext ctx) {
        ForwardReadyMessage readyMessage = new ForwardReadyMessage();
        ctx.writeAndFlush(readyMessage);
    }

    /**
     * forward server crashed.
     * close all connected.
     */
    public void crashed() {
        System.exit(1);
    }

    /**
     * connected server stopped.
     */
    public void serverStopped() {
        for (String key : this.contextMap.keySet()) {
            ChannelHandlerContext context = this.contextMap.get(key);
            context.close();
        }
        System.exit(0);
    }

    /**
     * connected server crashed.
     */
    public void serverCrashed() {
        System.exit(1);
    }
}
