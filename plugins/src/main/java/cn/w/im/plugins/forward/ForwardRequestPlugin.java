package cn.w.im.plugins.forward;

import cn.w.im.domains.MessageType;
import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.messages.forward.ForwardRequestMessage;
import cn.w.im.domains.messages.forward.ForwardResponseMessage;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.core.plugins.MessagePlugin;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午7:19.
 * Summary: respond response message for forward server request.
 */
public class ForwardRequestPlugin extends MessagePlugin<ForwardRequestMessage> {
    /**
     * 构造函数.
     */
    public ForwardRequestPlugin() {
        super("ForwardRequestPlugin", "respond response message for forward core request.");
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.ForwardRequest)
                && (context.getServer().getServerType() == ServerType.MessageBus);
    }

    @Override
    protected void processMessage(ForwardRequestMessage message, PluginContext context) throws ClientNotFoundException {
        ForwardResponseMessage responseMessage = new ForwardResponseMessage(context.getServer().getServerBasic());
        context.getServer().messageProvider().send(context.getCurrentHost(), context.getCurrentPort(), responseMessage);
    }
}
