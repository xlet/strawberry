package cn.w.im.core.allocate.provider;

import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.exception.ServerInnerException;

/**
 * when login server received member connected message from message server,
 * login server add this connected status to cache,but now the cache has same
 * connected status,then throw this exception.
 */
public class ConnectedStatusExistedException extends ServerInnerException {
    private String memberId;
    private MessageClientType clientType;

    public ConnectedStatusExistedException(String memberId, MessageClientType clientType) {
        super("message client[member:" + memberId + ",clientType:" + clientType + "] connected status existed.");
        this.memberId = memberId;
        this.clientType = clientType;
    }

    public String getMemberId() {
        return memberId;
    }

    public MessageClientType getClientType() {
        return clientType;
    }
}
