package cn.w.im.core.exception;

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
     * constructor.
     *
     * @param host      host.
     * @param port      port.
     * @param throwable inner throwable.
     */
    public ClientNotRegisterException(String host, int port, Throwable throwable) {
        super("the client[" + host + ":" + port + "] dit not register.", throwable);
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
