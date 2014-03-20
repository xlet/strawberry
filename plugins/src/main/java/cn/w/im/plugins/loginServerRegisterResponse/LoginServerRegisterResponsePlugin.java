package cn.w.im.plugins.loginServerRegisterResponse;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.SourceType;
import cn.w.im.domains.messages.responses.ServerRegisterResponseMessage;
import cn.w.im.domains.server.LoginServer;
import cn.w.im.domains.server.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;

import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-18 下午8:35.
 * Summary: process login server register response message.
 * add response started other login server.
 * <p/>
 * this plugin only added to LoginServer.
 */
public class LoginServerRegisterResponsePlugin extends MessagePlugin<ServerRegisterResponseMessage> {
    /**
     * 构造函数.
     */
    public LoginServerRegisterResponsePlugin(ServerType containerType) {
        super("LoginServerRegisterResponsePlugin", "add response started server basic.", containerType);
    }

    @Override
    public boolean isMatch(PluginContext context) {
        boolean isMatch = context.getMessage().getMessageType() == MessageType.ServerRegisterResponse;
        return isMatch;
    }

    @Override
    public void processMessage(ServerRegisterResponseMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (containerType()) {
            case LoginServer:
                processMessageWithLoginServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(containerType());
        }
    }

    private void processMessageWithLoginServer(ServerRegisterResponseMessage responseMessage, PluginContext context) {
        List<ServerBasic> startedOtherLoginServers = responseMessage.getStartedLoginServers();
        for (ServerBasic startedOtherLoginServer : startedOtherLoginServers) {
            LoginServer.current().addStartedOtherLoginServer(startedOtherLoginServer, SourceType.Pull);
        }

        List<ServerBasic> startedMessageServers = responseMessage.getStartedMessageServers();
        for (ServerBasic startedMessageServer : startedMessageServers) {
            LoginServer.current().addStartedMessageServer(startedMessageServer, SourceType.Pull);
            //todo jackie register to message server and request message server linked clients count.
        }
    }
}
