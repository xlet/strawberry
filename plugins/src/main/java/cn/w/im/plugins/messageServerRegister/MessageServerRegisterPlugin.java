package cn.w.im.plugins.messageServerRegister;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.client.MessageServerClient;
import cn.w.im.domains.messages.ServerRegisterMessage;
import cn.w.im.domains.messages.responses.ServerRegisterResponseMessage;
import cn.w.im.domains.server.MessageBus;
import cn.w.im.domains.server.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Iterator;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 上午11:49.
 * Summary: 消息服务注册消息处理. 支持消息总线服务.
 */
public class MessageServerRegisterPlugin extends MessagePlugin<ServerRegisterMessage> {

    private final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public MessageServerRegisterPlugin(ServerType containerType) {
        super("MessageServerRegisterPlugin", "record this message server and response other started message server to this message server.", containerType);
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
    public void processMessage(ServerRegisterMessage message, HandlerContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (this.containerType()) {
            case MessageBus:
                processMessageWithMessageBus(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageBus(ServerRegisterMessage message, HandlerContext context) {
        ServerBasic serverBasic = message.getServerBasic();
        //消息总线服务注册.
        MessageBus.current().addMessageServer(serverBasic, context.getCtx());
        //回复消息
        ServerRegisterResponseMessage responseMessage = new ServerRegisterResponseMessage(true);
        Iterator<Client> startedMessageServerIterator = MessageBus.current().getRegisteredMessageServers().iterator();
        while (startedMessageServerIterator.hasNext()) {
            MessageServerClient messageServerClient = (MessageServerClient) startedMessageServerIterator.next();
            if (!messageServerClient.getServerBasic().getNodeId().equals(serverBasic.getNodeId())) {
                responseMessage.addStartedMessageServer(messageServerClient.getServerBasic());
            }
        }
        context.getCtx().writeAndFlush(responseMessage);
    }
}
