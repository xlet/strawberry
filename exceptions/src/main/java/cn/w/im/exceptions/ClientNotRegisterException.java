package cn.w.im.exceptions;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午4:51.
 * Summary: client dit not register exception.
 */
public class ClientNotRegisterException extends ServerInnerException {

    private String host;
    private int port;

    /**
     * constructor.
     *
     * @param host host.
     * @param port port.
     */
    public ClientNotRegisterException(String host, int port) {
        super("the client[" + host + ":" + port + "] dit not register.");
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
