package cn.w.imserver.test;

import cn.w.im.message.LoginMessage;
import cn.w.im.message.LoginResponseMessage;
import cn.w.im.message.Message;
import cn.w.im.message.NormalMessage;
import cn.w.im.plugins.message.MessageHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Creator: JackieHan
 * DateTime: 13-12-16 上午10:32
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private String id, password;

    public ClientHandler(String id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Message message = new LoginMessage(id, password);
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof LoginResponseMessage){
            LoginResponseMessage message=(LoginResponseMessage)msg;
            if(message.isSuccess()){
                System.out.println("登陆成功!");
                new Console(ctx,id).start();
            }
            else{
                System.out.println("用户名密码错误!");
            }
        }
        else if(msg instanceof NormalMessage){
            NormalMessage normalMessage=(NormalMessage)msg;
            System.out.println(normalMessage.getFrom()+"-->"+normalMessage.getTo());
            System.out.println("SendDate:"+normalMessage.getSendTime());
            System.out.println("ReceiveDate"+new Date().toString());
            System.out.println("message:"+normalMessage.getContent());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
