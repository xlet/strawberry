package cn.w.im.plugins.forwardMessage;

import cn.w.im.core.plugins.PluginContext;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.server.ForwardMessage;
import cn.w.im.core.server.MessageBus;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.core.plugins.MessagePlugin;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午3:34.
 * Summary: 服务之间消息转发插件.
 */
public class ForwardMessagePlugin extends MessagePlugin<ForwardMessage> {

    /**
     * 构造函数.
     */
    public ForwardMessagePlugin() {
        super("ForwardMessagePlugin", "forward message between core and core.");
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.Forward)
                && (context.getServer().getServerType() == ServerType.MessageBus);
    }

    @Override
    public void processMessage(ForwardMessage message, PluginContext context) throws ClientNotFoundException {
        switch (context.getServer().getServerType()) {
            case MessageBus:
                processMessageWithMessageBus(message, context);
                break;
        }
    }

    private void processMessageWithMessageBus(ForwardMessage message, PluginContext context) throws ClientNotFoundException {
        context.getServer().messageProvider().send(message.getToServer(), message);
    }
}
