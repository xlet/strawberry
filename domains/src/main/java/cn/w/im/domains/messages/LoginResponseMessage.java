package cn.w.im.domains.messages;

import cn.w.im.domains.LoginToken;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-23 下午5:07.
 */
public class LoginResponseMessage extends Message {

    /**
     * 登陆是否成功.
     */
    private boolean success;

    private LoginToken token;

    /**
     * 获取是否成功.
     * @return 成功:true  失败:false.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 设置是否成功.
     * @param success 成功:true  失败:false.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 获取标识信息.
     * @return 标识信息.
     */
    public LoginToken getToken() {
        return token;
    }

    /**
     * 设置标识信息.
     * @param token 标识信息.
     */
    public void setToken(LoginToken token) {
        this.token = token;
    }

    /**
     * 构造函数.
     */
    public LoginResponseMessage() {
        super(MessageType.LoginResponse);
    }

    /**
     * 构造函数.
     * @param success 登陆是否成功.
     */
    public LoginResponseMessage(boolean success) {
        this();
        this.success = success;
    }

    /**
     * 构造函数.
     * @param success 成功:true  失败:false.
     * @param token 标识信息.
     */
    public LoginResponseMessage(boolean success, LoginToken token) {
        this(success);
        this.token = token;
    }

}
