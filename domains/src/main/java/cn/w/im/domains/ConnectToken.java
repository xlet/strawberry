package cn.w.im.domains;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午4:57.
 * Summary:
 */
public class ConnectToken {

    private String clientHost;

    private String loginId;

    private long loginDate;

    private String token;

    private ServerBasic allocatedMessageServer;

    /**
     * default constructor.
     */
    public ConnectToken() {

    }

    /**
     * constructor.
     *
     * @param clientHost            client host.
     * @param loginId               login id.
     * @param token                 token.
     * @param allocateMessageServer message server basic.
     */
    public ConnectToken(String clientHost, String loginId, String token, ServerBasic allocateMessageServer) {
        this.clientHost = clientHost;
        this.loginId = loginId;
        this.loginDate = new Date().getTime();
        this.token = token;
        this.allocatedMessageServer = allocateMessageServer;
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
    public String getLoginId() {
        return loginId;
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
