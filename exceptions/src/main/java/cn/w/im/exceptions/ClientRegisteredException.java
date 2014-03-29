package cn.w.im.exceptions;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午4:44.
 * Summary: client registered exception.
 */
public class ClientRegisteredException extends ServerInnerException {

    private String host;
    private int port;

    /**
     * constructor.
     */
    public ClientRegisteredException(String host, int port) {
        super("the client[" + host + ":" + port + "] is registered.");
        this.host = host;
        this.port = port;
    }

    /**
     * get host.
     *
     * @return host.
     */
    public String getHost() {
        return host;
    }

    /**
     * get port.
     *
     * @return port.
     */
    public int getPort() {
        return port;
    }
}
