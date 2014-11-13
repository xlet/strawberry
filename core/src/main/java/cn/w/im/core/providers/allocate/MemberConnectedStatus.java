package cn.w.im.core.providers.allocate;

import cn.w.im.core.MessageClientType;

/**
 * member connected status.
 */
public class MemberConnectedStatus {

    private String memberId;
    private MessageClientType clientType;
    private String connectedHost;

    public MemberConnectedStatus(String memberId, MessageClientType clientType, String connectedHost) {
        this.memberId = memberId;
        this.clientType = clientType;
        this.connectedHost = connectedHost;
    }

    public String getMemberId() {
        return memberId;
    }

    public MessageClientType getClientType() {
        return clientType;
    }

    public String getConnectedHost() {
        return connectedHost;
    }
}
