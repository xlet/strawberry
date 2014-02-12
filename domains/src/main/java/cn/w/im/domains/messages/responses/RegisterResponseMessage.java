package cn.w.im.domains.messages.responses;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.RegisterMessageType;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-10 下午2:31.
 * Summary: 注册回复消息.
 */
public class RegisterResponseMessage extends ResponseMessage {

    private RegisterMessageType registerMessageType;

    /**
     * 构造函数.
     */
    public RegisterResponseMessage() {
        super(MessageType.RegisterResponse);
    }

    /**
     * 构造函数.
     * @param success 注册成功:true.
     * @param registerMessageType 注册消息类型.
     */
    public RegisterResponseMessage(boolean success, RegisterMessageType registerMessageType) {
        this();
        this.setSuccess(success);
        this.registerMessageType = registerMessageType;
    }

    /**
     * 获取注册消息类型.
     * @return 注册消息类型.
     */
    public RegisterMessageType getRegisterMessageType() {
        return registerMessageType;
    }

    /**
     * 设置注册消息类型.
     * @param registerMessageType 注册消息类型.
     */
    public void setRegisterMessageType(RegisterMessageType registerMessageType) {
        this.registerMessageType = registerMessageType;
    }
}
