package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.Message;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 上午9:55.
 * Summary: message client linked message server message.
 */
public class ConnectMessage extends Message implements ClientToServerMessage {

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
     * @param loginId login id.
     * @param token   token String.
     */
    public ConnectMessage(String loginId, String token) {
        this();
        this.loginId = loginId;
        this.token = token;
    }

    /**
     * get login id.
     * @return login id.
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * set login id.
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
