package cn.w.im.core.message.client;

import cn.w.im.core.MessageType;
import cn.w.im.core.MessageClientType;
import cn.w.im.core.message.Message;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-24 上午9:57.
 */
public class LogoutMessage extends Message implements ClientToServerMessage {

    private MessageClientType clientType;
    private String memberId;

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
        this.memberId = loginId;
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
    public String getMemberId() {
        return this.memberId;
    }

    /**
     * 设置登陆id.
     *
     * @param memberId 登陆id.
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
