package cn.w.im.exceptions;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 上午10:59.
 * Summary: the server has registered exception.
 */
public class RegisteredRespondServerException extends ServerInnerException {

    private String serverNodeId;

    /**
     * constructor.
     *
     * @param serverNodeId server node id.
     */
    public RegisteredRespondServerException(String serverNodeId) {
        super("the core[" + serverNodeId + "] has registered exception.");
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
