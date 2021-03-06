package org.xlet.strawberry.testClient;

import org.xlet.strawberry.core.client.MessageClientType;
import org.xlet.strawberry.core.message.client.LogoutMessage;
import org.xlet.strawberry.core.message.client.NormalMessage;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import java.util.Scanner;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-24 上午11:28.
 * Summary: 命令行主程序.
 */
public class Console {

    private ChannelHandlerContext ctx;

    private String selfId;

    private MessageClientType clientType;

    /**
     * 构造函数.
     *
     * @param ctx    连接上下文.
     * @param selfId 自己id.
     */
    public Console(ChannelHandlerContext ctx, String selfId, MessageClientType clientType) {
        this.ctx = ctx;
        this.selfId = selfId;
        this.clientType = clientType;
    }

    /**
     * run.
     */
    public void run() {
        try {
            System.out.println("1.发送消息请按[1]");
            System.out.println("2.退出请按[2]");

            char insert = (char) System.in.read();
            switch (insert) {
                case '1':
                    sendMessage();
                    break;
                case '2':
                    logout();
                    break;
                default:
                    System.out.println("\n输入有误，请重新输入!");
                    break;
            }
        } catch (Exception ex) {
            System.out.println("发生未知错误!");
        }
    }

    /**
     * 退出登陆.
     */
    public void logout() {
        LogoutMessage logoutMessage = new LogoutMessage(this.clientType, this.selfId);
        ChannelFuture logoutFuture = ctx.writeAndFlush(logoutMessage);
        logoutFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                future.channel().close();
                System.exit(1);
            }
        });
    }

    /**
     * 发送消息.
     *
     * @throws Exception 异常.
     */
    public synchronized void sendMessage() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入接收方id:");
        String id = scanner.next();
        System.out.print("请输入要发送的消息:");
        String message = scanner.next();
        NormalMessage normalMessage = new NormalMessage(this.clientType, this.selfId, id, message);
        ctx.writeAndFlush(normalMessage);
        run();
    }
}
