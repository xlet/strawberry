package cn.w.im.domains.client;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午2:53.
 * Summary: 客户端类型.
 */
public enum ClientType {

    /**
     * 登录服务.
     */
    LoginServer(0x01),

    /**
     * 消息服务.
     */
    MessageServer(0x02),

    /**
     * 一般客户端.
     */
    MessageClient(0x03);

    private int value;

    private ClientType(int value) {
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
