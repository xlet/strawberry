package cn.w.im.domains.client;

import cn.w.im.domains.messages.client.ProductType;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-17 上午11:02.
 * Summary: 消息客户端基础信息.
 */
public class MessageClientBasic {

    private String memberId, clientHost;

    private int clientPort;

    private MessageClientType messageClientType;

    private ProductType productType;

    /**
     * 构造函数.
     */
    public MessageClientBasic() {

    }

    /**
     * 构造函数.
     *
     * @param productType       product type.
     * @param messageClientType messageClientType.
     * @param memberId          登陆id.
     * @param clientHost        客户端host.
     * @param clientPort        客户端端口号.
     */
    public MessageClientBasic(ProductType productType, MessageClientType messageClientType, String memberId, String clientHost, int clientPort) {
        this.productType = productType;
        this.messageClientType = messageClientType;
        this.memberId = memberId;
        this.clientHost = clientHost;
        this.clientPort = clientPort;
    }

    /**
     * get product type.
     *
     * @return product type.
     */
    public ProductType getProductType() {
        return productType;
    }

    /**
     * set product type.
     *
     * @param productType product type.
     */
    public void setProductType(ProductType productType) {
        this.productType = productType;
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
     * set client type.
     *
     * @param messageClientType client type.
     */
    public void setMessageClientType(MessageClientType messageClientType) {
        this.messageClientType = messageClientType;
    }

    /**
     * 获取登陆id.
     *
     * @return 登陆id.
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * 设置登陆id.
     *
     * @param memberId 登陆id.
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
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
