package cn.w.im.message;

/**
 * Creator: JackieHan
 * DateTime: 13-12-19 下午5:48
 */
public class LoginMessage extends Message {

    /**
     * 用户Id
     */
    private String id;

    /**
     * 用户密码
     */
    private String password;

    public LoginMessage(){
        super(MessageType.Login);
    }

    public LoginMessage(String id,String password){
        super(MessageType.Login);
        this.id=id;
        this.password=password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
