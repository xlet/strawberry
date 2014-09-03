package cn.w.im.exceptions;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午5:21.
 * Summary: server dit not register exception.
 */
public class ServerNotRegisterException extends ServerInnerException {

    private String nodeId;

    /**
     * constructor.
     *
     * @param nodeId server node id.
     */
    public ServerNotRegisterException(String nodeId) {
        super("the core[" + nodeId + "] dit not register.");
        this.nodeId = nodeId;
    }

    /**
     * get server node id.
     *
     * @return server node id.
     */
    public String getNodeId() {
        return nodeId;
    }
}
