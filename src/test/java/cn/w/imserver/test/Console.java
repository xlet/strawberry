package cn.w.imserver.test;

import cn.w.im.message.NormalMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;

import java.util.Scanner;

/**
 * Creator: JackieHan
 * DateTime: 13-12-24 上午11:28
 */
public class Console extends Thread{

    private ChannelHandlerContext ctx;

    private String selfId;

    public Console(ChannelHandlerContext ctx,String selfId){
        this.ctx=ctx;
        this.selfId=selfId;
    }

    public void run(){
        try{
        System.out.println("1.发送消息请按[1]");
        System.out.println("2.退出请按[2]");

        char insert=(char)System.in.read();
        switch (insert){
            case '1':
                sendMessage();
                break;
            case '2':
                break;
            default:
                System.out.println("\n输入有误，请重新输入!");
                break;
        }
        }
        catch (Exception ex)
        {
            System.out.println("发生未知错误!");
        }
    }

    public void sendMessage() throws Exception{
        Scanner scanner=new Scanner(System.in);
        System.out.println("请输入接收方id:");
        String id=scanner.next();
        System.out.print("请输入要发送的消息:");
        String message=scanner.next();
        NormalMessage normalMessage=new NormalMessage(this.selfId,id,message);
        ctx.writeAndFlush(normalMessage);
        run();
    }
}
