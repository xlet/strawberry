package cn.w.im.plugins.requestLinkedClients;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.client.MessageClientBasic;
import cn.w.im.domains.messages.ForwardMessage;
import cn.w.im.domains.messages.RequestLinkedClientsMessage;
import cn.w.im.domains.messages.responses.ResponseLinkedClientsMessage;
import cn.w.im.domains.server.MessageServer;
import cn.w.im.domains.server.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-17 下午3:13.
 * Summary: 处理请求已连接客户端信息插件.
 */
public class RequestLinkedClientsPlugin extends MessagePlugin<RequestLinkedClientsMessage> {

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public RequestLinkedClientsPlugin(ServerType containerType) {
        super("RequestLinkedClientsPlugin", "message server request other server linked clients.", containerType);
    }

    @Override
    public boolean isMatch(HandlerContext context) {
        return context.getMessage().getMessageType() == MessageType.RequestLinkedClients;
    }

    @Override
    public void processMessage(RequestLinkedClientsMessage message, HandlerContext context) throws NotSupportedServerTypeException, ClientNotFoundException {
        switch (this.containerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(RequestLinkedClientsMessage message, HandlerContext context) {
        List<MessageClientBasic> clients = MessageServer.current().getLinkedClients();
        ResponseLinkedClientsMessage responseMessage = new ResponseLinkedClientsMessage(MessageServer.current().getServerBasic(), clients);
        ForwardMessage forwardMessage = new ForwardMessage(MessageServer.current().getServerBasic(), message.getRequestServer(), responseMessage);
        MessageServer.current().getForwardContext().writeAndFlush(forwardMessage);
    }
}
