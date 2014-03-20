package cn.w.im.plugins.forwardMessage;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.Client;
import cn.w.im.domains.messages.ForwardMessage;
import cn.w.im.domains.server.MessageBus;
import cn.w.im.domains.server.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午3:34.
 * Summary: 服务之间消息转发插件.
 */
public class ForwardMessagePlugin extends MessagePlugin<ForwardMessage> {

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public ForwardMessagePlugin(ServerType containerType) {
        super("ForwardMessagePlugin", "forward message between server and server.", containerType);
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return context.getMessage() instanceof ForwardMessage;
    }

    @Override
    public void processMessage(ForwardMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (this.containerType()) {
            case MessageBus:
                processMessageWithMessageBus(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageBus(ForwardMessage message, PluginContext context) throws ClientNotFoundException {
        ServerBasic toServer = message.getToServer();
        Client client = MessageBus.current().getClient(toServer);
        if (client == null) {
            throw new ClientNotFoundException(toServer.getNodeId());
        }
        client.getContext().writeAndFlush(message.getMessage());
    }
}
