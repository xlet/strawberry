package cn.w.im.domains.messages;

import cn.w.im.domains.MessageType;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 上午9:55.
 * Summary: 连接消息服务器消息.
 */
public class ConnectMessage extends Message {

    private String token;

    /**
     * 构造函数.
     */
    public ConnectMessage() {
        super(MessageType.Connect);
    }

    /**
     * 构造函数.
     */
    public ConnectMessage(String token) {
        this();
        this.token = token;
    }

    /**
     * 获取token.
     *
     * @return token.
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置token.
     *
     * @param token token.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
