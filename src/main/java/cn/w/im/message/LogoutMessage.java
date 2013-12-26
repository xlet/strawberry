package cn.w.im.message;

/**
 * Creator: JackieHan
 * DateTime: 13-12-24 上午9:57
 */
public class LogoutMessage extends Message {

    private String id;

    public LogoutMessage(){
        super(MessageType.Logout);
    }

    public LogoutMessage(String id){
        this();
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
