package cn.w.im.core.message.client;

import cn.w.im.core.ConnectToken;
import cn.w.im.core.MessageType;
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

    private ServerBasic allocateServer;

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
     * get allocate server.
     *
     * @return allocate server.
     */
    public ServerBasic getAllocateServer() {
        return allocateServer;
    }

    /**
     * set allocate server.
     *
     * @param allocateServer allocate server.
     */
    public void setAllocateServer(ServerBasic allocateServer) {
        this.allocateServer = allocateServer;
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
        this.token = token.getToken();
        this.memberId = token.getMember().getId();
        this.allocateServer = token.getAllocatedMessageServer();
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
