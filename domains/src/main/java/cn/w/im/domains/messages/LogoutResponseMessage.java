package cn.w.im.domains.messages;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-24 上午11:09.
 * Summary:登出返回消息.
 */
public class LogoutResponseMessage extends Message {

    /**
     * 是否成功.
     */
    private boolean success;

    /**
     * 构造函数.
     */
    public LogoutResponseMessage() {
        super(MessageType.LogoutResponse);
    }

    /**
     * 构造函数.
     * @param success 是否成功.
     */
    public LogoutResponseMessage(boolean success) {
        this();
        this.success = success;
    }

    /**
     * 获取是否成功.
     * @return 成功:true  失败:false.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 设置是否成功.
     * @param success 成功:true  失败:false.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
