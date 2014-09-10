package cn.w.im.plugins.innerForwardMessage;

import cn.w.im.core.plugins.PluginContext;
import cn.w.im.core.server.MessageServer;
import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.core.plugins.MessagePlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 下午4:48.
 * Summary: 转发一般消息.
 * one server
 * message ----------------> client
 * * |                        /|\
 * * | other server            |
 * \|/        the server      |
 * messageBus  --------->   messageServer
 */
public class InnerForwardMessagePlugin extends MessagePlugin<NormalMessage> {

    /**
     * 日志.
     */
    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * 构造函数.
     */
    public InnerForwardMessagePlugin() {
        super("InnerForwardMessagePlugin", "forward message to client.");
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return (context.getMessage().getMessageType() == MessageType.Normal)
                && (context.getServer().getServerType() == ServerType.MessageServer);
    }

    @Override
    public void processMessage(NormalMessage message, PluginContext context) throws ClientNotFoundException {
        switch (context.getServer().getServerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
        }
    }

    private void processMessageWithMessageServer(NormalMessage message, PluginContext context) {
        context.getServer().messageProvider().send(message.getTo(), message);
    }
}
