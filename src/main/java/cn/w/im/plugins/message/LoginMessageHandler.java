package cn.w.im.plugins.message;

import cn.w.im.message.LoginMessage;
import cn.w.im.message.LoginResponseMessage;
import cn.w.im.message.Message;
import cn.w.im.server.ClientInfo;

import java.net.InetSocketAddress;

/**
 * Creator: JackieHan
 * DateTime: 13-12-24 下午2:55
 * Summary:登陆消息处理
 */
public class LoginMessageHandler extends AbstractMessageHandler {

    @Override
    public void process(Message message) {
        LoginMessage loginMessage = (LoginMessage) message;
        InetSocketAddress remoteAddress = (InetSocketAddress) this.getCtx().channel().remoteAddress();
        String ipAddress = remoteAddress.getHostString();
        int port = remoteAddress.getPort();
        ClientInfo client = new ClientInfo(ipAddress, port, this.getCtx(), loginMessage.getId());
        this.getServerInfo().getClients().add(client);

        //TODO:jackiehan 添加登陆验证
        this.getCtx().writeAndFlush(new LoginResponseMessage(true));
    }
}
