package cn.w.im.domains.messages.client;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.ResponseMessage;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-23 下午5:07.
 * login response message.
 * if login success,response messageServer basic and linked token.
 * if login error:
 * user id and password error,response error message.
 * user logged in other place,response error message with logged in place to tell user has force logged out.
 */
public class LoginResponseMessage extends ResponseMessage implements ServerToClientMessage {

    private ConnectToken token;

    private String loggedOtherPlace;

    /**
     * 获取标识信息.
     *
     * @return 标识信息.
     */
    public ConnectToken getToken() {
        return token;
    }

    /**
     * 设置标识信息.
     *
     * @param token 标识信息.
     */
    public void setToken(ConnectToken token) {
        this.token = token;
    }

    /**
     * get logged other place.
     *
     * @return logged other place.
     */
    public String getLoggedOtherPlace() {
        return loggedOtherPlace;
    }

    /**
     * set logged other place.
     *
     * @param loggedOtherPlace logged other place.
     */
    public void setLoggedOtherPlace(String loggedOtherPlace) {
        this.loggedOtherPlace = loggedOtherPlace;
    }

    /**
     * 构造函数.
     */
    public LoginResponseMessage() {
        super(MessageType.LoginResponse);
    }

    /**
     * 构造函数.
     *
     * @param token connect token.
     */
    public LoginResponseMessage(ConnectToken token) {
        super(MessageType.LoginResponse);
        this.token = token;
    }

    /**
     * error response.
     *
     * @param errorCode        error code.
     * @param errorMessage     error message.
     * @param loggedOtherPlace logged other place.
     */
    public LoginResponseMessage(int errorCode, String errorMessage, String loggedOtherPlace) {
        super(MessageType.LoginResponse, errorCode, errorMessage);
        this.loggedOtherPlace = loggedOtherPlace;
    }

    /**
     * error response.
     *
     * @param errorCode    error code.
     * @param errorMessage error message.
     */
    public LoginResponseMessage(int errorCode, String errorMessage) {
        this(errorCode, errorMessage, "");
    }
}
