package cn.w.im.domains;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-17 下午4:36.
 */
public enum MessageType {

    /**
     * 登陆消息.
     */
    Login(0x01),
    /**
     * 登陆返回消息.
     */
    LoginResponse(0x02),
    /**
     * 登出消息.
     */
    Logout(0x03),
    /**
     * 登出回复消息.
     */
    LogoutResponse(0x04),
    /**
     * 一般消息.
     */
    Normal(0x05),
    /**
     * 系统消息.
     */
    System(0x06),
    /**
     * 消息服务注册消息(消息服务注册到消息总线服务).
     */
    MessageServerRegister(0x07),
    /**
     * 所有注册消息的回复消息.
     */
    RegisterResponse(0x08),

    /**
     * 登陆服务注册消息(注册到消息总线服务).
     */
    LoginServerRegister(0x09);

    /**
     * 构造函数.
     * @param value 参数值.
     */
    private MessageType(int value) {
        this.value = value;
    }

    /**
     * 获取值.
     * @return 值.
     */
    public int getValue() {
        return this.value;
    }

    private int value;
}
