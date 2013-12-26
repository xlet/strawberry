package cn.w.im.utils.exceptions;

/**
 * Creator: JackieHan
 * DateTime: 13-12-25 上午11:23
 */
public class ClientNotFoundException extends RuntimeException{
    public String id;

    public ClientNotFoundException(String id){
        super("Client["+id+"] not found!");
        this.id=id;
    }

    public String getId() {
        return id;
    }
}
