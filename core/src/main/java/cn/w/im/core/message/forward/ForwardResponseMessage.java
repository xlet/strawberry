package cn.w.im.core.message.forward;

import cn.w.im.core.MessageType;
import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.message.ResponseMessage;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午4:36.
 * Summary: forward ready respond
 */
public class ForwardResponseMessage extends ResponseMessage {

    private ServerBasic fromServer;

    /**
     * default constructor.
     */
    public ForwardResponseMessage() {
        super(MessageType.ForwardResponse);
    }

    /**
     * constructor.
     *
     * @param fromServer from server basic.
     */
    public ForwardResponseMessage(ServerBasic fromServer) {
        this();
        this.fromServer = fromServer;
    }

    /**
     * error constructor.
     *
     * @param errorCode    error code.
     * @param errorMessage error message.
     */
    public ForwardResponseMessage(int errorCode, String errorMessage) {
        super(MessageType.ForwardResponse, errorCode, errorMessage);
    }

    /**
     * get from server basic.
     *
     * @return from server basic.
     */
    public ServerBasic getFromServer() {
        return fromServer;
    }

    /**
     * set from server basic.
     *
     * @param fromServer from server basic.
     */
    public void setFromServer(ServerBasic fromServer) {
        this.fromServer = fromServer;
    }
}
