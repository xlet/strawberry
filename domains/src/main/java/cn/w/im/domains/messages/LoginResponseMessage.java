package cn.w.im.domains.messages;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-23 下午5:07.
 */
public class LoginResponseMessage extends Message {

    /**
     * 登陆是否成功.
     */
    private boolean success;

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

    /**
     * 构造函数.
     */
    public LoginResponseMessage() {
        super(MessageType.LoginResponse);
    }

    /**
     * 构造函数.
     * @param success 成功:true  失败:false.
     */
    public LoginResponseMessage(boolean success) {
        this();
        this.success = success;
    }

}
