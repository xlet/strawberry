package cn.w.im.domains.client;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-17 上午11:02.
 * Summary: 消息客户端基础信息.
 */
public class MessageClientBasic {

    private String loginId, clientHost;

    private int clientPort;

    private MessageClientType messageClientType;

    /**
     * 构造函数.
     */
    public MessageClientBasic() {

    }

    /**
     * 构造函数.
     *
     * @param messageClientType messageClientType.
     * @param loginId           登陆id.
     * @param clientHost        客户端host.
     * @param clientPort        客户端端口号.
     */
    public MessageClientBasic(MessageClientType messageClientType, String loginId, String clientHost, int clientPort) {
        this.messageClientType = messageClientType;
        this.loginId = loginId;
        this.clientHost = clientHost;
        this.clientPort = clientPort;
    }

    /**
     * get client type.
     *
     * @return client type.
     */
    public MessageClientType getMessageClientType() {
        return this.messageClientType;
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
     * 设置登陆id.
     *
     * @param loginId 登陆id.
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * 获取客户端host.
     *
     * @return 客户端host.
     */
    public String getClientHost() {
        return clientHost;
    }

    /**
     * 设置客户端host.
     *
     * @param clientHost 客户端host.
     */
    public void setClientHost(String clientHost) {
        this.clientHost = clientHost;
    }

    /**
     * 获取客户端端口号.
     *
     * @return 客户端端口号.
     */
    public int getClientPort() {
        return clientPort;
    }

    /**
     * 设置客户端端口号.
     *
     * @param clientPort 客户端端口号.
     */
    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }
}
