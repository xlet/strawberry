package org.xlet.strawberry.testClient;

import org.xlet.strawberry.core.ProductType;
import org.xlet.strawberry.core.client.MessageClientType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xlet.strawberry.core.message.client.ConnectMessage;
import org.xlet.strawberry.core.message.client.ConnectResponseMessage;
import org.xlet.strawberry.core.message.client.NormalMessage;

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
    private static Logger log;

    private String token;

    private String memberId;

    private MessageClientType clientType;

    private ProductType productType;

    /**
     * 构造函数.
     */
    public ConnectHandler(String token, String memberId, MessageClientType clientType, ProductType productType) {
        this.token = token;
        this.memberId = memberId;
        log = LoggerFactory.getLogger(this.getClass());
        this.clientType = clientType;
        this.productType = productType;
    }


    /**
     * channelActive.
     *
     * @param ctx 当前连接上下文.
     * @throws Exception 异常.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ConnectMessage connectMessage = new ConnectMessage(this.productType, this.clientType,
                this.memberId, this.token);
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
            final Console console = new Console(ctx, this.memberId, this.clientType);
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
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
