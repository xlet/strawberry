package cn.w.im.testClient;

import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.LoginMessage;
import cn.w.im.domains.messages.LoginResponseMessage;
import cn.w.im.domains.messages.LogoutResponseMessage;
import cn.w.im.domains.messages.NormalMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-16 上午10:32.
 * Summary: 客户端netty handler.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 日志
     */
    private static Log log = LogFactory.getLog(ClientHandler.class);

    /**
     * id:登陆id.
     * password:登陆密码.
     */
    private String id, password;

    /**
     * 构造函数.
     * @param id 登陆id.
     * @param password 登陆密码.
     */
    public ClientHandler(String id, String password) {
        this.id = id;
        this.password = password;
    }

    /**
     * channelActive.
     * @param ctx 当前连接上下文.
     * @throws Exception 异常.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Message message = new LoginMessage(id, password);
        ctx.writeAndFlush(message);
    }

    /**
     * channelRead.
     * @param ctx 上下文.
     * @param msg 消息.
     * @throws Exception 异常.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof LoginResponseMessage) {
            LoginResponseMessage message = (LoginResponseMessage) msg;
            if (message.isSuccess()) {
                System.out.println("登陆成功!");
                new Console(ctx, id).start();
            } else {
                System.out.println("用户名密码错误!");
            }
        } else if (msg instanceof NormalMessage) {
            NormalMessage normalMessage = (NormalMessage) msg;
            System.out.println(normalMessage.getFrom() + "-->" + normalMessage.getTo());
            System.out.println("SendDate:" + normalMessage.getSendTime());
            System.out.println("ReceiveDate" + new Date().toString());
            System.out.println("message:" + normalMessage.getContent());
        } else if (msg instanceof LogoutResponseMessage) {
            LogoutResponseMessage logoutResponseMessage = (LogoutResponseMessage) msg;
            if (logoutResponseMessage.isSuccess()) {
                System.out.println("登出成功!");
                ctx.close();
            } else {
                System.out.println("登出失败!");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
