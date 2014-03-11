package cn.w.im.plugins.messageServerRegister;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.ServerRegisterMessage;
import cn.w.im.domains.server.LoginServer;
import cn.w.im.domains.server.MessageBus;
import cn.w.im.domains.server.ServerType;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 上午11:49.
 * Summary: 消息服务注册消息处理. 支持消息总线服务，登陆服务.
 */
public class MessageServerRegisterPlugin extends MessagePlugin {

    private final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public MessageServerRegisterPlugin(ServerType containerType) {
        super("MessageServerRegisterPlugin", "process message server register message.", containerType);
    }

    @Override
    public boolean isMatch(HandlerContext context) {
        boolean isMatch = context.getMessage().getMessageType() == MessageType.ServerRegister;
        if (!isMatch) {
            return false;
        }
        ServerRegisterMessage serverRegisterMessage = (ServerRegisterMessage) context.getMessage();
        return serverRegisterMessage.getServerType() == ServerType.MessageServer;
    }

    @Override
    public void processMessage(Message message, HandlerContext context) {
        ServerRegisterMessage registerMessage = (ServerRegisterMessage) message;

        switch (this.containerType()) {
            case MessageBus:
                processMessageWithMessageBus(registerMessage, context);
                break;
            case LoginServer:
                processMessageWithLoginServer(registerMessage, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithLoginServer(ServerRegisterMessage message, HandlerContext context) {
        ServerBasic serverBasic = message.getServerBasic();
        LoginServer.current().addMessageServer(serverBasic);
    }

    private void processMessageWithMessageBus(ServerRegisterMessage message, HandlerContext context) {
        ServerBasic serverBasic = message.getServerBasic();
        //消息总线服务注册.
        MessageBus.current().addMessageServer(serverBasic, context.getCtx());
        //转发到登陆服务.
        List<Client> loginServers = MessageBus.current().getRegisteredLoginServers();
        for (Client client : loginServers) {
            client.getContext().writeAndFlush(message);
        }
    }
}
