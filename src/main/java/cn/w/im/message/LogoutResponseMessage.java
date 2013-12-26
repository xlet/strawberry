package cn.w.im.message;

/**
 * Creator: JackieHan
 * DateTime: 13-12-24 上午11:09
 * Summary:登出返回消息
 */
public class LogoutResponseMessage extends Message{

    private boolean success;

    public LogoutResponseMessage(){
        super(MessageType.LogoutResponse);
    }

    public LogoutResponseMessage(boolean success){
        this();
        this.success=success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
