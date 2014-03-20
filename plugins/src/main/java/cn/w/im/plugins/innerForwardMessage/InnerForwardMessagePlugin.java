package cn.w.im.plugins.innerForwardMessage;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.ServerBasic;
import cn.w.im.domains.client.MessageClient;
import cn.w.im.domains.messages.ForwardMessage;
import cn.w.im.domains.server.MessageServer;
import cn.w.im.domains.messages.NormalMessage;
import cn.w.im.domains.server.ServerType;
import cn.w.im.exceptions.ClientNotFoundException;
import cn.w.im.exceptions.NotSupportedServerTypeException;
import cn.w.im.plugins.MessagePlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 下午4:48.
 * Summary: 转发一般消息.
 * one server
 * message ----------------> client
 *  |                        /|\
 *  | other server            |
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
     *
     * @param containerType 服务类型.
     */
    public InnerForwardMessagePlugin(ServerType containerType) {
        super("InnerForwardMessagePlugin", "forward message to client.", containerType);
    }

    @Override
    public boolean isMatch(PluginContext context) {
        return context.getMessage() instanceof NormalMessage;
    }

    @Override
    public void processMessage(NormalMessage message, PluginContext context) throws ClientNotFoundException, NotSupportedServerTypeException {
        switch (this.containerType()) {
            case MessageServer:
                processMessageWithMessageServer(message, context);
                break;
            default:
                throw new NotSupportedServerTypeException(this.containerType());
        }
    }

    private void processMessageWithMessageServer(NormalMessage message, PluginContext context) {
        MessageClient toClient = MessageServer.current().getClient(message.getTo());
        if (toClient != null) {
            toClient.getContext().write(message);
            message.setForward(true);
        } else {
            ServerBasic otherServer = MessageServer.current().getOtherServer(message.getTo());
            if (otherServer != null) {
                ForwardMessage forwardMessage = new ForwardMessage(MessageServer.current().getServerBasic(), otherServer, message);
                MessageServer.current().getForwardContext().writeAndFlush(forwardMessage);
            }
        }
    }
}
