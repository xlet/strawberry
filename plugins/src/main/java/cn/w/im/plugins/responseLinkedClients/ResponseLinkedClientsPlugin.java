package cn.w.im.plugins.responseLinkedClients;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.MessageClientBasic;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.responses.ResponseLinkedClientsMessage;
import cn.w.im.domains.server.MessageServer;
import cn.w.im.domains.server.ServerType;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-17 下午3:39.
 * Summary: 处理请求所有已连接客户端消息响应消息插件.
 */
public class ResponseLinkedClientsPlugin extends MessagePlugin {

    /**
     * 构造函数.
     * @param containerType 服务类型.
     */
    public ResponseLinkedClientsPlugin(ServerType containerType) {
        super("ResponseLinkedClientsPlugin", "request linked clients response message process.", containerType);
    }

    @Override
    public boolean isMatch(HandlerContext context) {
        return context.getMessage().getMessageType() == MessageType.ResponseLinkedClients;
    }

    @Override
    public void processMessage(Message message, HandlerContext context) {
        ResponseLinkedClientsMessage responseMessage = (ResponseLinkedClientsMessage) message;
        switch (this.containerType()) {
            case MessageServer:
                processMessageWithMessageServer(responseMessage, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(ResponseLinkedClientsMessage message, HandlerContext context) {
        List<MessageClientBasic> clients = message.getLinkedClients();
        ServerBasic messageServer = message.getMessageServer();
        MessageServer.current().addOtherServerClients(messageServer, clients);
    }
}
