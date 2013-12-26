package cn.w.im.message;

/**
 * Creator: JackieHan
 * DateTime: 13-12-23 下午5:07
 */
public class LoginResponseMessage extends Message {

    /**
     * 登陆是否成功
     */
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public LoginResponseMessage(){
        super(MessageType.LoginResponse);
    }

    public LoginResponseMessage(boolean success){
        this();
        this.success=success;
    }

}
