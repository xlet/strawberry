package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.Message;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-24 上午9:57.
 */
public class LogoutMessage extends Message implements ClientToServerMessage {

    private MessageClientType clientType;
    private String loginId;

    /**
     * 构造函数.
     */
    public LogoutMessage() {
        super(MessageType.Logout);
    }

    /**
     * 构造函数.
     *
     * @param clientType message client type.
     * @param loginId    登陆id.
     */
    public LogoutMessage(MessageClientType clientType, String loginId) {
        this();
        this.clientType = clientType;
        this.loginId = loginId;
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
     * 获取登陆id.
     *
     * @return 登陆id.
     */
    public String getLoginId() {
        return this.loginId;
    }

    /**
     * 设置登陆id.
     *
     * @param loginId 登陆id.
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
}
