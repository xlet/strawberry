package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.Message;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 上午9:55.
 * Summary: message client linked message server message.
 */
public class ConnectMessage extends Message implements ClientToServerMessage {

    private ProductType productType;

    private MessageClientType clientType;

    private String loginId;

    private String token;

    /**
     * constructor.
     */
    public ConnectMessage() {
        super(MessageType.Connect);
    }

    /**
     * constructor.
     *
     * @param productType product type.
     * @param clientType  message client type.
     * @param loginId     login id.
     * @param token       token String.
     */
    public ConnectMessage(ProductType productType, MessageClientType clientType, String loginId, String token) {
        this();
        this.productType = productType;
        this.clientType = clientType;
        this.loginId = loginId;
        this.token = token;
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
     * get message client type.
     *
     * @return message client type.
     */
    public MessageClientType getClientType() {
        return this.clientType;
    }

    /**
     * set message client type.
     *
     * @param clientType message client type.
     */
    public void setClientType(MessageClientType clientType) {
        this.clientType = clientType;
    }

    /**
     * get login id.
     *
     * @return login id.
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * set login id.
     *
     * @param loginId login id.
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * get token string.
     *
     * @return token string.
     */
    public String getToken() {
        return token;
    }

    /**
     * set token string.
     *
     * @param token token String.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
