package cn.w.im.core.message.client;

import cn.w.im.core.allocate.ConnectToken;
import cn.w.im.core.message.MessageType;
import cn.w.im.core.server.ServerBasic;
import cn.w.im.core.message.ResponseMessage;

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

    private String token;

    private String memberId;

    private String messageHost;

    private int messagePort;

    private String loggedOtherPlace;

    /**
     * 获取标识信息.
     *
     * @return 标识信息.
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置标识信息.
     *
     * @param token 标识信息.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * get member id.
     *
     * @return member id.
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * set member id.
     *
     * @param memberId member id.
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * get message host.
     *
     * @return message host.
     */
    public String getMessageHost() {
        return messageHost;
    }

    /**
     * set message host.
     *
     * @param messageHost message host.
     */
    public void setMessageHost(String messageHost) {
        this.messageHost = messageHost;
    }

    /**
     * get message port.
     *
     * @return message port.
     */
    public int getMessagePort() {
        return messagePort;
    }

    /**
     * set message port.
     *
     * @param messagePort message port.
     */
    public void setMessagePort(int messagePort) {
        this.messagePort = messagePort;
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
     * @param token       token.
     * @param memberId    member id.
     * @param messageHost message host.
     * @param messagePort message port.
     */
    public LoginResponseMessage(String token, String memberId, String messageHost, int messagePort) {
        super(MessageType.LoginResponse);
        this.token = token;
        this.memberId = memberId;
        this.messageHost = messageHost;
        this.messagePort = messagePort;
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
