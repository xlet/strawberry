package cn.w.im.web.imserver;

import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.client.LoginMessage;
import cn.w.im.domains.messages.client.LoginResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author jackie.
 */
public class LoginHandler extends ChannelInboundHandlerAdapter {
    /**
     * 日志
     */
    private static Log log;

    /**
     * id:登陆id.
     * password:登陆密码.
     */
    private String id;

    private MessageClientType clientType;

    private boolean completed=false;

    private LoginResponseMessage responseMessage = null;

    public boolean isCompleted(){
        return this.completed;
    }

    public LoginResponseMessage getResponseMessage(){
        return this.responseMessage;
    }

    /**
     * 构造函数.
     *
     * @param clientType message client type.
     * @param id         登陆id.
     */
    public LoginHandler(MessageClientType clientType, String id) {
        this.id = id;
        this.clientType = clientType;
        log = LogFactory.getLog(this.getClass());
    }


    /**
     * channelActive.
     *
     * @param ctx 当前连接上下文.
     * @throws Exception 异常.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoginMessage loginMessage = new LoginMessage(this.clientType, this.id);
        ctx.writeAndFlush(loginMessage);
    }

    /**
     * channelRead.
     *
     * @param ctx 上下文.
     * @param msg 消息.
     * @throws Exception 异常.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof LoginResponseMessage) {
            LoginResponseMessage message = (LoginResponseMessage) msg;
            this.completed=true;
            this.responseMessage = message;
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
