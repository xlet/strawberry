package cn.w.im.plugins.messageServerRegisterResponse;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.SourceType;
import cn.w.im.domains.messages.responses.ServerRegisterResponseMessage;
import cn.w.im.domains.server.MessageServer;
import cn.w.im.domains.server.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午5:03.
 * Summary:
 */
public class MessageServerRegisterResponsePlugin extends MessagePlugin<ServerRegisterResponseMessage> {

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public MessageServerRegisterResponsePlugin(ServerType containerType) {
        super("MessageServerRegisterResponsePlugin", "add response started message server to local and send request linked clients message to these message server.", containerType);
    }

    @Override
    public boolean isMatch(PluginContext context) {
        boolean isMatch = context.getMessage().getMessageType() == MessageType.ServerRegisterResponse;
        return isMatch;
    }

    @Override
    public void processMessage(ServerRegisterResponseMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (this.containerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(ServerRegisterResponseMessage message, PluginContext context) {
        if (message.isSuccess()) {
            List<ServerBasic> startedLoginServers = message.getStartedLoginServers();
            MessageServer.current().addStartedLoginServers(startedLoginServers, SourceType.Pull);
            List<ServerBasic> startedOtherMessageServers = message.getStartedMessageServers();
            MessageServer.current().addStartedOtherMessageServers(startedOtherMessageServers, SourceType.Pull);
            if (message.getStartedMessageServers().size() == 0) {
                MessageServer.current().ready();
            } else {
                MessageServer.current().requestLinkedClients();
            }
        }
    }
}
