package cn.w.im.core;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午5:54.
 * Summary: 服务类型.
 */
public enum ServerType {

    /**
     * 登录服务.
     */
    LoginServer(0x01),

    /**
     * 消息服务.
     */
    MessageServer(0x02),

    /**
     * 消息总线服务.
     */
    MessageBus(0x03);

    private int value;

    private ServerType(int value) {
        this.value = value;
    }

    /**
     * 获取值.
     *
     * @return 值.
     */
    public int getValue() {
        return this.value;
    }
}
