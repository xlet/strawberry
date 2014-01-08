package cn.w.im.exceptions;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-25 上午11:23.
 * Summary:客户端没有找到异常.
 */
public class ClientNotFoundException extends RuntimeException {

    /**
     * 客户端登陆id.
     */
    public String id;

    /**
     * 构造函数.
     *
     * @param id 客户端登陆id.
     */
    public ClientNotFoundException(String id) {
        super("Client[" + id + "] not found!");
        this.id = id;
    }

    /**
     * 获取登陆Id.
     *
     * @return 登陆id.
     */
    public String getId() {
        return id;
    }
}
