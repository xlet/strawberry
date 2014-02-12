package cn.w.im.domains.client;

import io.netty.channel.ChannelHandlerContext;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-17 下午3:59.
 * Summary: 一般消息客户端.
 */
public class MessageClient extends Client {

    /**
     * 登陆id.
     */
    private String id;

    /**
     * 获取登陆id.
     * @return 登陆id.
     */
    public String getId() {
        return id;
    }

     /**
     * 构造函数.
     * @param id 登陆id.
     */
    public MessageClient(ChannelHandlerContext context, String id) {
        super(ClientType.MessageClient);
        this.setContext(context);
        this.id = id;
    }
}
