package cn.w.im.domains.conf;

/**
 * forward server config.
 */
public class ForwardConfiguration {

    private int serverPort,busPort;
    private String serverHost,busHost;
    private boolean nettyInnerLoggerEnable;

    /**
     * get server port.
     * @return server port.
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * set server port.
     * @param serverPort server port.
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * get bus port.
     * @return bus port.
     */
    public int getBusPort() {
        return busPort;
    }

    /**
     * set bus port.
     * @param busPort bus port.
     */
    public void setBusPort(int busPort) {
        this.busPort = busPort;
    }

    /**
     * get server host.
     * @return server host.
     */
    public String getServerHost() {
        return serverHost;
    }

    /**
     * set server host.
     * @param serverHost server host.
     */
    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    /**
     * get bus host.
     * @return bus host.
     */
    public String getBusHost() {
        return busHost;
    }

    /**
     * set bus host.
     * @param busHost bus host.
     */
    public void setBusHost(String busHost) {
        this.busHost = busHost;
    }

    public boolean isNettyInnerLoggerEnable() {
        return nettyInnerLoggerEnable;
    }

    public void setNettyInnerLoggerEnable(boolean nettyInnerLoggerEnable) {
        this.nettyInnerLoggerEnable = nettyInnerLoggerEnable;
    }
}
