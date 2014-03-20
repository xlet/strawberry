package cn.w.im.plugins.loginServerRegister;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.client.LoginServerClient;
import cn.w.im.domains.client.MessageServerClient;
import cn.w.im.domains.messages.ServerRegisterMessage;
import cn.w.im.domains.messages.responses.ServerRegisterResponseMessage;
import cn.w.im.domains.server.MessageBus;
import cn.w.im.domains.server.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;

import java.util.Iterator;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-14 下午7:22.
 * Summary: process ServerRegisterMessage and ServerType is LoginServer
 * add login server to message bus.
 * <p/>
 * this plugin only add to message bus server.
 */
public class LoginServerRegisterPlugin extends MessagePlugin<ServerRegisterMessage> {
    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public LoginServerRegisterPlugin(ServerType containerType) {
        super("loginServerRegisterPlugin", "login server register to message bus server.", containerType);
    }

    @Override
    public boolean isMatch(HandlerContext context) {
        boolean isMatch = context.getMessage().getMessageType() == MessageType.ServerRegister;
        if (!isMatch) {
            return false;
        }
        ServerRegisterMessage serverRegisterMessage = (ServerRegisterMessage) context.getMessage();
        return serverRegisterMessage.getServerType() == ServerType.LoginServer;
    }

    @Override
    public void processMessage(ServerRegisterMessage message, HandlerContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (containerType()) {
            case MessageBus:
                processMessageWithMessageBus(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageBus(ServerRegisterMessage registerMessage, HandlerContext context) {
        ServerBasic loginServer = registerMessage.getServerBasic();
        //注册服务
        MessageBus.current().addLoginServer(loginServer, context.getCtx());
        //回复
        ServerRegisterResponseMessage responseMessage = new ServerRegisterResponseMessage(true);
        Iterator<Client> registeredLoginServerIterator = MessageBus.current().getRegisteredLoginServers().iterator();
        while (registeredLoginServerIterator.hasNext()) {
            LoginServerClient loginServerClient = (LoginServerClient) registeredLoginServerIterator.next();
            if (!loginServer.getNodeId().equals(loginServer.getNodeId())) {
                responseMessage.addStartedLoginServer(loginServerClient.getServerBasic());
            }
        }
        Iterator<Client> registeredMessageServerIterator = MessageBus.current().getRegisteredMessageServers().iterator();
        while (registeredMessageServerIterator.hasNext()) {
            MessageServerClient messageServerClient = (MessageServerClient) registeredMessageServerIterator.next();
            responseMessage.addStartedMessageServer(messageServerClient.getServerBasic());
        }
        context.getCtx().writeAndFlush(responseMessage);
    }
}
