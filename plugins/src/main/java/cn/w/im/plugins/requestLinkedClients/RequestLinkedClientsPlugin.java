package cn.w.im.plugins.requestLinkedClients;

import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.client.MessageClient;
import cn.w.im.domains.client.MessageClientBasic;
import cn.w.im.domains.messages.server.RequestLinkedClientsMessage;
import cn.w.im.domains.messages.server.ResponseLinkedClientsMessage;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.core.server.MessageServer;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.core.plugins.MessagePlugin;
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
     */
    public RequestLinkedClientsPlugin() {
        super("RequestLinkedClientsPlugin", "message core request other core linked clients.");
        this.logger = LogFactory.getLog(this.getClass());
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.RequestLinkedClients)
                && (context.getServer().getServerType() == ServerType.MessageServer);
    }

    @Override
    public void processMessage(RequestLinkedClientsMessage message, PluginContext context) throws ClientNotFoundException {
        switch (context.getServer().getServerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
        }
    }

    private void processMessageWithMessageServer(RequestLinkedClientsMessage message, PluginContext context) {
        try {
            context.getServer().clientCacheProvider().registerClient(message.getFromServer(), context.getCurrentHost(), context.getCurrentPort());
            Collection<Client> clients = context.getServer().clientCacheProvider().getAllMessageClients();
            List<MessageClientBasic> clientBasics = new ArrayList<MessageClientBasic>();
            for (Client client : clients) {
                MessageClient messageClient = (MessageClient) client;
                clientBasics.add(new MessageClientBasic(messageClient.getMessageClientType(), messageClient.getLoginId(), messageClient.getRemoteHost(), messageClient.getRemotePort()));
            }

            ResponseLinkedClientsMessage responseMessage = new ResponseLinkedClientsMessage(context.getServer().getServerBasic(), clientBasics, message.getRespondKey());
            context.getServer().messageProvider().send(message.getRequestServer(), responseMessage);
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
