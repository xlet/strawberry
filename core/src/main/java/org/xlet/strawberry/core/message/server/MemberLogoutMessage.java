package org.xlet.strawberry.core.message.server;

import org.xlet.strawberry.core.client.MessageClientType;
import org.xlet.strawberry.core.message.Message;
import org.xlet.strawberry.core.message.MessageType;
import org.xlet.strawberry.core.server.ServerBasic;

/**
 * member logout message.
 * when message server received message client logout message,send this message to login server to notify this client logout.
 */
public class MemberLogoutMessage extends Message {

    private String memberId;

    private String clientHost;

    private MessageClientType clientType;

    private ServerBasic serverBasic;

    public MemberLogoutMessage() {
        super(MessageType.MemberLogout);
    }

    public MemberLogoutMessage(String memberId, String clientHost, MessageClientType clientType, ServerBasic serverBasic) {
        this();
        this.memberId = memberId;
        this.clientHost = clientHost;
        this.clientType = clientType;
        this.serverBasic = serverBasic;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getClientHost() {
        return clientHost;
    }

    public void setClientHost(String clientHost) {
        this.clientHost = clientHost;
    }

    public MessageClientType getClientType() {
        return clientType;
    }

    public void setClientType(MessageClientType clientType) {
        this.clientType = clientType;
    }

    public ServerBasic getServerBasic() {
        return serverBasic;
    }

    public void setServerBasic(ServerBasic serverBasic) {
        this.serverBasic = serverBasic;
    }
}
