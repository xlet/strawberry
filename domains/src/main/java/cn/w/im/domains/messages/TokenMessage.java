package cn.w.im.domains.messages;

import cn.w.im.domains.LoginToken;
import cn.w.im.domains.MessageType;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午4:17.
 * Summary: token消息. 用于通知消息服务token信息.
 */
public class TokenMessage extends Message {

    private LoginToken token;

    /**
     * 构造函数.
     */
    public TokenMessage() {
        super(MessageType.Token);
    }

    /**
     * 构造函数.
     * @param token token信息.
     */
    public TokenMessage(LoginToken token) {
        this();
        this.token = token;
    }

    /**
     * 获取token.
     * @return token.
     */
    public LoginToken getToken() {
        return token;
    }

    /**
     * 设置token.
     * @param token token.
     */
    public void setToken(LoginToken token) {
        this.token = token;
    }
}
