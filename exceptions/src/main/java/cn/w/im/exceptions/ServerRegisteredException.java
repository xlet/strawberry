package cn.w.im.exceptions;

import cn.w.im.domains.ServerBasic;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-25 下午4:48.
 * Summary: server registered exception.
 */
public class ServerRegisteredException extends ServerInnerException {

    private ServerBasic serverBasic;

    /**
     * constructor.
     */
    public ServerRegisteredException(ServerBasic serverBasic) {
        super("the core[" + serverBasic.getNodeId() + "] is registered.");
        this.serverBasic = serverBasic;
    }

    /**
     * get server basic.
     *
     * @return server basic.
     */
    public ServerBasic getServerBasic() {
        return serverBasic;
    }
}
