package cn.w.im.plugins.messageServerRegisterResponse;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.messages.ForwardMessage;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.RequestLinkedClientsMessage;
import cn.w.im.domains.messages.responses.MessageServerRegisterResponseMessage;
import cn.w.im.domains.server.MessageServer;
import cn.w.im.domains.server.ServerType;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;

import java.util.Iterator;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午5:03.
 * Summary:
 */
public class MessageServerRegisterResponsePlugin extends MessagePlugin {

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public MessageServerRegisterResponsePlugin(ServerType containerType) {
        super("MessageServerRegisterResponsePlugin", "message bus server response message server register.", containerType);
    }

    @Override
    public boolean isMatch(HandlerContext context) {
        return context.getMessage() instanceof MessageServerRegisterResponseMessage;
    }

    @Override
    public void processMessage(Message message, HandlerContext context) {
        MessageServerRegisterResponseMessage responseMessage = (MessageServerRegisterResponseMessage) message;
        switch (this.containerType()) {
            case MessageServer:
                processMessageWithMessageServer(responseMessage, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(MessageServerRegisterResponseMessage message, HandlerContext context) {
        if (message.isSuccess()) {
            Iterator<ServerBasic> serverIterator = message.getStartedServers().iterator();
            while (serverIterator.hasNext()) {
                ServerBasic serverBasic = serverIterator.next();
                if (!serverBasic.getNodeId().equals(MessageServer.current().getNodeId())) {
                    MessageServer.current().addServer(serverBasic);
                    requestLinkedClients(serverBasic);
                }
            }
        }
    }

    private void requestLinkedClients(ServerBasic serverBasic) {
        RequestLinkedClientsMessage requestMessage = new RequestLinkedClientsMessage(MessageServer.current().getServerBasic());
        ForwardMessage forwardMessage = new ForwardMessage(MessageServer.current().getServerBasic(), serverBasic, requestMessage);
        MessageServer.current().getForwardContext().writeAndFlush(forwardMessage);
    }
}
