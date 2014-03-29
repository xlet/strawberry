package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.Message;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-24 上午9:57.
 */
public class LogoutMessage extends Message implements ClientToServerMessage {

    /**
     * 登陆id.
     */
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
     * @param loginId 登陆id.
     */
    public LogoutMessage(String loginId) {
        this();
        this.loginId = loginId;
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
