package cn.w.im.plugins.serverRegister;

import cn.w.im.domains.PluginContext;
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
 * Summary: process ServerRegisterMessage
 * add server to message bus.
 * <p/>
 * this plugin only add to message bus server.
 */
public class ServerRegisterPlugin extends MessagePlugin<ServerRegisterMessage> {
    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public ServerRegisterPlugin(ServerType containerType) {
        super("serverRegisterPlugin", "server register to message bus server.", containerType);
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return context.getMessage().getMessageType() == MessageType.ServerRegister;
    }

    @Override
    public void processMessage(ServerRegisterMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (containerType()) {
            case MessageBus:
                processMessageWithMessageBus(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageBus(ServerRegisterMessage registerMessage, PluginContext context) throws NotSupportedServerTypeException {

        ServerBasic serverBasic = registerMessage.getServerBasic();
        //注册服务
        switch (registerMessage.getServerType()) {
            case LoginServer:
                MessageBus.current().addLoginServer(serverBasic, context.getCtx());
                break;
            case MessageServer:
                MessageBus.current().addMessageServer(serverBasic, context.getCtx());
                break;
            default:
                throw new NotSupportedServerTypeException(registerMessage.getServerType());

        }

        //ToDo:jackie split this plugin to two plugin(register plugin and response plugin)

        //回复
        ServerRegisterResponseMessage responseMessage = new ServerRegisterResponseMessage(true);
        Iterator<Client> registeredLoginServerIterator = MessageBus.current().getRegisteredLoginServers().iterator();
        while (registeredLoginServerIterator.hasNext()) {
            LoginServerClient loginServerClient = (LoginServerClient) registeredLoginServerIterator.next();
            if (!loginServerClient.getNodeId().equals(serverBasic.getNodeId())) {
                responseMessage.addStartedLoginServer(loginServerClient.getServerBasic());
            }
        }
        Iterator<Client> registeredMessageServerIterator = MessageBus.current().getRegisteredMessageServers().iterator();
        while (registeredMessageServerIterator.hasNext()) {
            MessageServerClient messageServerClient = (MessageServerClient) registeredMessageServerIterator.next();
            if (!messageServerClient.getNodeId().equals(serverBasic.getNodeId())) {
                responseMessage.addStartedMessageServer(messageServerClient.getServerBasic());
            }
        }
        context.getCtx().writeAndFlush(responseMessage);
    }
}
