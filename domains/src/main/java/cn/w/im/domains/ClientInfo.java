package cn.w.im.domains;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-17 下午3:59.
 * Summary: 登陆到服务器上的客户端信息.
 */
public class ClientInfo {

    /**
     * 登陆id.
     */
    private String id;

    /**
     * 当前Handler上下文.
     */
    private HandlerContext context;

    /**
     * 获取登陆id.
     * @return 登陆id.
     */
    public String getId() {
        return id;
    }

    /**
     * 获取当前Handler上下文.
     * @return 当前Hander上下文.
     */
    public HandlerContext getContext() {
        return this.context;
    }

     /**
     * 构造函数.
     * @param id 登陆id.
     */
    public ClientInfo(HandlerContext context, String id) {
        this.context = context;
        this.id = id;
    }
}
