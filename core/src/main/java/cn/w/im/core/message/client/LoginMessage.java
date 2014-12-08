package cn.w.im.core.message.client;

import cn.w.im.core.ProductType;
import cn.w.im.core.message.MessageType;
import cn.w.im.core.client.MessageClientType;
import cn.w.im.core.message.Message;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-19 下午5:48.
 */
public class LoginMessage extends Message implements ClientToServerMessage {

    private String loginId;

    private String password;

    private MessageClientType clientType;

    private ProductType productType;

    /**
     * 默认构造函数.
     */
    public LoginMessage() {
        super(MessageType.Login);
    }

    /**
     * 构造函数.
     *
     * @param clientType  message client type.
     * @param productType product type.
     * @param loginId     登陆Id.
     * @param password    密码.
     */
    public LoginMessage(MessageClientType clientType, ProductType productType, String loginId, String password) {
        super(MessageType.Login);
        this.clientType = clientType;
        this.productType = productType;
        this.loginId = loginId;
        this.password = password;
    }

    /**
     * constructor.
     *
     * @param clientType message client type.
     * @param loginId    login id.
     */
    public LoginMessage(MessageClientType clientType, String loginId) {
        super(MessageType.Login);
        this.clientType = clientType;
        this.loginId = loginId;
    }

    /**
     * 获取登陆id.
     *
     * @return loginId.
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * 设置登陆id.
     *
     * @param loginId loginId.
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * 获取密码.
     *
     * @return 密码.
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码.
     *
     * @param password 密码.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get message client type.
     *
     * @return message client type.
     */
    public MessageClientType getClientType() {
        return clientType;
    }

    /**
     * set message client type.
     *
     * @param clientType message client Type.
     */
    public void setClientType(MessageClientType clientType) {
        this.clientType = clientType;
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
}
