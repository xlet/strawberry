package cn.w.im.plugins.forward;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.messages.forward.ForwardReadyMessage;
import cn.w.im.domains.messages.server.ServerRegisterMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;
import cn.w.im.core.server.ServerInstance;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午7:34.
 * Summary: message server/login server send ServerRegisterMessage to message bus server when forward service is ready.
 * message bus server is nothing to do.
 */
public class ForwardReadyPlugin extends MessagePlugin<ForwardReadyMessage> {

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public ForwardReadyPlugin(ServerType containerType) {
        super("ForwardReadyPlugin", "register to message bus core.", containerType);
    }

    @Override
    protected boolean isMatch(PluginContext context) {
        return context.getMessage().getMessageType() == MessageType.ForwardReady;
    }

    @Override
    protected void processMessage(ForwardReadyMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (this.containerType()) {
            case LoginServer:
            case MessageServer:
                registerThisServerToMessageBus(context);
                break;
            case MessageBus:
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void registerThisServerToMessageBus(PluginContext context) throws NotSupportedServerTypeException {
        ServerRegisterMessage serverRegisterMessage = new ServerRegisterMessage(ServerInstance.current(this.containerType()).getServerBasic());
        ServerInstance.current(this.containerType()).messageProvider().send(context.getCurrentHost(), context.getCurrentPort(), serverRegisterMessage);
    }
}
