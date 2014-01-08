package cn.w.im.testClient;

import cn.w.im.domains.messages.LogoutMessage;
import cn.w.im.domains.messages.NormalMessage;
import io.netty.channel.ChannelHandlerContext;

import java.util.Scanner;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-24 上午11:28.
 * Summary: 命令行主程序.
 */
public class Console extends Thread {

    /**
     * 连接上下文.
     */
    private ChannelHandlerContext ctx;

    /**
     * 自己id.
     */
    private String selfId;

    /**
     * 构造函数.
     *
     * @param ctx    连接上下文.
     * @param selfId 自己id.
     */
    public Console(ChannelHandlerContext ctx, String selfId) {
        this.ctx = ctx;
        this.selfId = selfId;
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
        LogoutMessage logoutMessage = new LogoutMessage(this.selfId);
        ctx.writeAndFlush(logoutMessage);
    }

    /**
     * 发送消息.
     * @throws Exception 异常.
     */
    public void sendMessage() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入接收方id:");
        String id = scanner.next();
        System.out.print("请输入要发送的消息:");
        String message = scanner.next();
        NormalMessage normalMessage = new NormalMessage(this.selfId, id, message);
        ctx.writeAndFlush(normalMessage);
        run();
    }
}