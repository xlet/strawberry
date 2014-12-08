package cn.w.im.core.allocate.provider;

import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.exception.ServerInnerException;

/**
 * when message client disconnected from message server,
 * message server send member logout message to login server.
 * login server remove connected status cache,but connected status
 * not existed,then throw this exception.
 */
public class ConnectedStatusNotExistedException extends ServerInnerException {

    private String memberId;
    private MessageClientType clientType;
    private String clientHost;

    public ConnectedStatusNotExistedException(String memberId, MessageClientType clientType, String clientHost) {
        super("message client status[member:" + memberId + "],clientType:" + clientType + ",clientHost:" + clientHost + "] not existed.");
        this.memberId = memberId;
        this.clientType = clientType;
        this.clientHost = clientHost;
    }

    public String getMemberId() {
        return memberId;
    }

    public MessageClientType getClientType() {
        return clientType;
    }

    public String getClientHost() {
        return clientHost;
    }
}
