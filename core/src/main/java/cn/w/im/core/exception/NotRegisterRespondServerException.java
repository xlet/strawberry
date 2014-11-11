package cn.w.im.core.exception;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 上午10:56.
 * Summary:
 */
public class NotRegisterRespondServerException extends ServerInnerException {

    private String serverNodeId;

    /**
     * constructor.
     *
     * @param serverNodeId not register respond server node id.
     */
    public NotRegisterRespondServerException(String serverNodeId) {
        super("the responded core[" + serverNodeId + "] has not registered.");
        this.serverNodeId = serverNodeId;
    }

    /**
     * get server node id.
     *
     * @return server node id.
     */
    public String getServerNodeId() {
        return serverNodeId;
    }
}
