package cn.w.im.domains;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-10 下午2:46.
 * Summary: 注册消息类型.
 */
public enum RegisterMessageType {

    /**
     * 消息服务器注册消息.
     */
    MessageServerRegister(0x01);

    private int value;

    private RegisterMessageType(int value) {
        this.value = value;
    }

    /**
     * 获取值.
     * @return 获取值.
     */
    public int getValue() {
        return this.value;
    }
}
