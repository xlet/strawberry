package cn.w.im.testClient;

import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.client.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-16 上午10:32.
 * Summary: 客户端netty handler.
 */
public class LoginHandler extends ChannelInboundHandlerAdapter {

    /**
     * 日志
     */
    private static Logger log;

    private List<HandlerListener> listeners = new ArrayList<HandlerListener>();

    /**
     * id:登陆id.
     * password:登陆密码.
     */
    private String id, password, token;

    private MessageClientType clientType;


    /**
     * 构造函数.
     *
     * @param clientType message client type.
     * @param id         登陆id.
     * @param password   登陆密码.
     */
    public LoginHandler(MessageClientType clientType, String id, String password) {
        this.id = id;
        this.password = password;
        this.clientType = clientType;
        log = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * 构造函数.
     *
     * @param clientType message client type.
     * @param token      login token.
     */
    public LoginHandler(MessageClientType clientType, String token) {
        this.clientType = clientType;
        this.token = token;
    }

    /**
     * 添加监听.
     *
     * @param listener 监听回调.
     */
    public void addListener(HandlerListener listener) {
        listeners.add(listener);
    }

    /**
     * channelActive.
     *
     * @param ctx 当前连接上下文.
     * @throws Exception 异常.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (!id.equals("") && !password.equals("")) {
            LoginMessage loginMessage = new LoginMessage(this.clientType, this.id, this.password);
            ctx.writeAndFlush(loginMessage);
        } else {
            ConnectMessage connectMessage = new ConnectMessage(this.clientType, this.id, this.token);
            ctx.writeAndFlush(connectMessage);
        }
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
            PluginContext handlerContext = new PluginContext(message, ctx, null);
            ctx.close();
            for (HandlerListener listener : listeners) {
                listener.operationComplete(handlerContext);
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
