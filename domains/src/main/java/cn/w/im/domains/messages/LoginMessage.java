package cn.w.im.domains.messages;

import cn.w.im.domains.MessageType;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-19 下午5:48.
 */
public class LoginMessage extends Message {

    /**
     * 用户Id.
     */
    private String loginId;

    /**
     * 用户密码.
     */
    private String password;

    /**
     * 默认构造函数.
     */
    public LoginMessage() {
        super(MessageType.Login);
    }

    /**
     * 构造函数.
     * @param loginId 登陆Id.
     * @param password 密码.
     */
    public LoginMessage(final String loginId, final String password) {
        super(MessageType.Login);
        this.loginId = loginId;
        this.password = password;
    }

    /**
     * 获取登陆id.
     * @return loginId.
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * 设置登陆id.
     * @param loginId loginId.
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * 获取密码.
     * @return 密码.
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码.
     * @param password 密码.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
