package cn.w.im.plugins.requestLinkedClients;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.client.MessageClient;
import cn.w.im.domains.client.MessageClientBasic;
import cn.w.im.domains.messages.server.RequestLinkedClientsMessage;
import cn.w.im.domains.messages.server.ResponseLinkedClientsMessage;
import cn.w.im.server.MessageServer;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-17 下午3:13.
 * Summary: 处理请求已连接客户端信息插件.
 */
public class RequestLinkedClientsPlugin extends MessagePlugin<RequestLinkedClientsMessage> {

    private Log logger;

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public RequestLinkedClientsPlugin(ServerType containerType) {
        super("RequestLinkedClientsPlugin", "message server request other server linked clients.", containerType);
        this.logger = LogFactory.getLog(this.getClass());
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return context.getMessage().getMessageType() == MessageType.RequestLinkedClients;
    }

    @Override
    public void processMessage(RequestLinkedClientsMessage message, PluginContext context) throws NotSupportedServerTypeException, ClientNotFoundException {
        switch (this.containerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(RequestLinkedClientsMessage message, PluginContext context) {
        Collection<Client> clients = MessageServer.current().clientCacheProvider().getAllMessageClients();
        List<MessageClientBasic> clientBasics = new ArrayList<MessageClientBasic>();
        for (Client client : clients) {
            MessageClient messageClient = (MessageClient) client;
            clientBasics.add(new MessageClientBasic(messageClient.getLoginId(), messageClient.getRemoteHost(), messageClient.getRemotePort()));
        }

        ResponseLinkedClientsMessage responseMessage = new ResponseLinkedClientsMessage(MessageServer.current().getServerBasic(), clientBasics, message.getRespondKey());
        MessageServer.current().sendMessageProvider().send(message.getRequestServer(), responseMessage);
    }
}
