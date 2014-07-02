package cn.w.im.web.imserver;

import cn.w.im.domains.client.MessageClientType;
import cn.w.im.domains.messages.client.ConnectResponseMessage;
import cn.w.im.domains.messages.client.LogoutResponseMessage;
import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.domains.messages.client.WebServerConnectMessage;
import cn.w.im.domains.messages.server.ConnectedResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 * @author jackie.
 */
public class WebMessageHandler extends ChannelInboundHandlerAdapter {

    private final static MessageClientType MESSAGE_CLIENT_TYPE = MessageClientType.Web;
    private final static String CONNECT_TOKEN="normalWebToken1";
    /**
     * 日志
     */
    private final static Log LOG = LogFactory.getLog(WebMessageHandler.class);

    private ChannelHandlerContext ctx;

    /**
     * get channel handler context.
     * @return channel handler context.
     */
    public ChannelHandlerContext getCtx() {
        return this.ctx;
    }


    /**
     * channelActive.
     *
     * @param ctx 当前连接上下文.
     * @throws Exception 异常.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        WebServerConnectMessage connectMessage = new WebServerConnectMessage(CONNECT_TOKEN);
        this.ctx.writeAndFlush(connectMessage);
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
        if (msg instanceof ConnectResponseMessage){
            if (((ConnectResponseMessage) msg).isSuccess()){
                this.ctx = ctx;
                LOG.debug("connect success.");
            }
            else{
                //todo jackie how to process when connected failed!?
                LOG.debug("connect failed.");
            }
        }
        else if (msg instanceof NormalMessage) {
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
