package cn.w.im.core.status.memberAll;

import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.exception.ServerInnerException;
import cn.w.im.core.member.BasicMember;

/**
 * one member connect a connected client type exception.
 */
public class ClientTypeConnectedException extends ServerInnerException {

    private BasicMember member;
    private MessageClientType clientType;
    private String connectedHost;
    private int connectedPort;

    public ClientTypeConnectedException(BasicMember member, MessageClientType clientType, String connectedHost, int connectedPort) {
        super("member[" + member.getId() + "],client[" + clientType + "],connectedHost["
                + connectedHost + "],connectedPort[" + connectedPort + "] is connected.");
        this.member = member;
        this.clientType = clientType;
        this.connectedHost = connectedHost;
        this.connectedPort = connectedPort;
    }

    public BasicMember getMember() {
        return member;
    }

    public MessageClientType getClientType() {
        return clientType;
    }

    public String getConnectedHost() {
        return connectedHost;
    }

    public int getConnectedPort() {
        return connectedPort;
    }
}
