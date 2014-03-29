package cn.w.im.domains.client;

import io.netty.channel.ChannelHandlerContext;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-17 下午3:59.
 * Summary: message client.
 */
public class MessageClient extends Client {

    private String loginId;

    /**
     * get login id.
     * @return login id.
     */
    public String getLoginId() {
        return this.loginId;
    }

     /**
     * constructor.
     * @param loginId login id.
     */
    public MessageClient(ChannelHandlerContext context, String loginId) {
        super(context);
        this.loginId = loginId;
    }
}
