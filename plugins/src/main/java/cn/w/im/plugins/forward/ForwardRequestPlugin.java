package cn.w.im.plugins.forward;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.messages.forward.ForwardRequestMessage;
import cn.w.im.domains.messages.forward.ForwardResponseMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;
import cn.w.im.server.ServerInstance;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午7:19.
 * Summary: respond response message for forward server request.
 */
public class ForwardRequestPlugin extends MessagePlugin<ForwardRequestMessage> {
    /**
     * 构造函数.
     */
    public ForwardRequestPlugin(ServerType containerType) {
        super("ForwardRequestPlugin", "respond response message for forward server request.", containerType);
    }

    @Override
    protected boolean isMatch(PluginContext context) {
        return context.getMessage().getMessageType() == MessageType.ForwardRequest;
    }

    @Override
    protected void processMessage(ForwardRequestMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        ForwardResponseMessage responseMessage = new ForwardResponseMessage(ServerInstance.current(this.containerType()).getServerBasic());
        ServerInstance.current(this.containerType()).sendMessageProvider().send(context.getCurrentHost(), context.getCurrentPort(), responseMessage);
    }
}
