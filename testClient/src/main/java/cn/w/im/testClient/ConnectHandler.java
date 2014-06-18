package cn.w.im.testClient;

import cn.w.im.domains.ConnectToken;
import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.client.ConnectMessage;
import cn.w.im.domains.messages.client.ConnectResponseMessage;
import cn.w.im.domains.messages.client.LogoutResponseMessage;
import cn.w.im.domains.messages.client.NormalMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-4-1 下午7:38.
 * Summary:
 */
public class ConnectHandler extends ChannelInboundHandlerAdapter {

    /**
     * 日志
     */
    private static Log log;

    private ConnectToken connectToken;

    private MessageClientType clientType;

    /**
     * 构造函数.
     */
    public ConnectHandler(ConnectToken connectToken, MessageClientType messageClientType) {
        this.connectToken = connectToken;
        log = LogFactory.getLog(this.getClass());
        this.clientType = clientType;
    }


    /**
     * channelActive.
     *
     * @param ctx 当前连接上下文.
     * @throws Exception 异常.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ConnectMessage connectMessage = new ConnectMessage(this.clientType, this.connectToken.getLoginId(), this.connectToken.getToken());
        ctx.writeAndFlush(connectMessage);
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
        if (msg instanceof ConnectResponseMessage) {
            final Console console = new Console(ctx, this.connectToken.getLoginId(), this.clientType);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    console.run();
                }
            }).start();
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
