package cn.w.im.plugins.forward;

import cn.w.im.domains.MessageType;
import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.messages.forward.ForwardReadyMessage;
import cn.w.im.domains.messages.server.ServerRegisterMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.core.plugins.MessagePlugin;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午7:34.
 * Summary: message server/login server send ServerRegisterMessage to message bus server when forward service is ready.
 * message bus server is nothing to do.
 */
public class ForwardReadyPlugin extends MessagePlugin<ForwardReadyMessage> {

    /**
     * 构造函数.
     */
    public ForwardReadyPlugin() {
        super("ForwardReadyPlugin", "register to message bus core.");
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.ForwardReady)
                && ((context.getServer().getServerType() == ServerType.MessageServer)
                || (context.getServer().getServerType() == ServerType.LoginServer));
    }

    @Override
    protected void processMessage(ForwardReadyMessage message, PluginContext context) throws ClientNotFoundException {
        switch (context.getServer().getServerType()) {
            case LoginServer:
            case MessageServer:
                registerThisServerToMessageBus(context);
                break;
        }
    }

    private void registerThisServerToMessageBus(PluginContext context) {
        ServerRegisterMessage serverRegisterMessage = new ServerRegisterMessage(context.getServer().getServerBasic());
        context.getServer().messageProvider().send(context.getCurrentHost(), context.getCurrentPort(), serverRegisterMessage);
    }
}
