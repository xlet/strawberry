package cn.w.im.core;

import cn.w.im.core.member.BasicMember;
import cn.w.im.core.server.ServerBasic;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午4:57.
 * Summary:
 */
public class ConnectToken {

    private String clientHost;

    private BasicMember member;

    private long loginDate;

    private String token;

    private ServerBasic allocatedMessageServer;

    private MessageClientType clientType;

    /**
     * default constructor.
     */
    public ConnectToken() {

    }

    /**
     * constructor.
     *
     * @param clientType            client type.
     * @param clientHost            client host.
     * @param member                member.
     * @param token                 token.
     * @param allocateMessageServer message server basic.
     */
    public ConnectToken(MessageClientType clientType, String clientHost, BasicMember member,
                        String token, ServerBasic allocateMessageServer) {
        this.clientHost = clientHost;
        this.member = member;
        this.loginDate = new Date().getTime();
        this.token = token;
        this.allocatedMessageServer = allocateMessageServer;
        this.clientType = clientType;
    }

    /**
     * get client type.
     *
     * @return client type.
     */
    public MessageClientType getClientType() {
        return clientType;
    }

    /**
     * 获取客户端Ip地址.
     *
     * @return ip地址.
     */
    public String getClientHost() {
        return clientHost;
    }

    /**
     * 获取登陆id.
     *
     * @return 登陆id.
     */
    public BasicMember getMember() {
        return member;
    }

    /**
     * 获取登陆时间.
     *
     * @return 登陆时间.
     */
    public long getLoginDate() {
        return loginDate;
    }

    /**
     * 获取标识.
     *
     * @return 标识.
     */
    public String getToken() {
        return token;
    }

    /**
     * get allocated message server.
     *
     * @return allocated message server.
     */
    public ServerBasic getAllocatedMessageServer() {
        return this.allocatedMessageServer;
    }
}
