package cn.w.im.domains.messages.responses;

import cn.w.im.domains.LoginToken;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-23 下午5:07.
 */
public class LoginResponseMessage extends ResponseMessage {

    private LoginToken token;

    private ServerBasic messageServer;

    /**
     * 获取标识信息.
     *
     * @return 标识信息.
     */
    public LoginToken getToken() {
        return token;
    }

    /**
     * 设置标识信息.
     *
     * @param token 标识信息.
     */
    public void setToken(LoginToken token) {
        this.token = token;
    }

    /**
     * 获取消息服务信息.
     *
     * @return 消息服务信息.
     */
    public ServerBasic getMessageServer() {
        return messageServer;
    }

    /**
     * 设置消息服务信息.
     *
     * @param messageServer 消息服务信息.
     */
    public void setMessageServer(ServerBasic messageServer) {
        this.messageServer = messageServer;
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
     * @param success 登陆是否成功.
     */
    public LoginResponseMessage(boolean success) {
        this();
        this.setSuccess(success);
    }

    /**
     * 构造函数.
     *
     * @param success       成功:true  失败:false.
     * @param token         标识信息.
     * @param messageServer 消息服务信息.
     */
    public LoginResponseMessage(boolean success, LoginToken token, ServerBasic messageServer) {
        this(success);
        this.token = token;
        this.messageServer = messageServer;
    }

}
