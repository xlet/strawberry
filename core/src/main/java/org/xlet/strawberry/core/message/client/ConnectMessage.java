package org.xlet.strawberry.core.message.client;

import org.xlet.strawberry.core.ProductType;
import org.xlet.strawberry.core.message.MessageType;
import org.xlet.strawberry.core.client.MessageClientType;
import org.xlet.strawberry.core.message.Message;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 上午9:55.
 * Summary: message client linked message server message.
 */
public class ConnectMessage extends Message implements ClientToServerMessage {

    private ProductType productType;

    private MessageClientType clientType;

    private String memberId;

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
     * @param memberId    member id.
     * @param token       token String.
     */
    public ConnectMessage(ProductType productType, MessageClientType clientType, String memberId, String token) {
        this();
        this.productType = productType;
        this.clientType = clientType;
        this.memberId = memberId;
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
    public String getMemberId() {
        return memberId;
    }

    /**
     * set login id.
     *
     * @param memberId login id.
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
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
