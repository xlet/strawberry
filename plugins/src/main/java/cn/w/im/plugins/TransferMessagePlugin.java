package cn.w.im.plugins;

import cn.w.im.domains.ClientInfo;
import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.ServerInfo;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.NormalMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 下午4:48.
 * Summary: 转发消息插件.
 */
public class TransferMessagePlugin extends MessagePlugin {

    /**
     * 日志.
     */
    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * 构造函数.
     */
    public TransferMessagePlugin() {
        super("TransferMessagePlugin", "转发消息插件");
    }

    @Override
    public boolean isMatch(HandlerContext context) {
        return context.getMessage() instanceof NormalMessage;
    }

    @Override
    public void processMessage(Message message, HandlerContext context) {
        try {
            NormalMessage normalMessage = (NormalMessage) message;
            ClientInfo toClient = ServerInfo.current().getClient(normalMessage.getTo());
            if (toClient != null) {
                toClient.getContext().write(normalMessage);
                normalMessage.setTransmit(true);
            }
        } catch (Exception ex) {
            logger.error("发生错误!", ex);
        }
    }
}
