package cn.w.im.plugins.persistentMessage;

import cn.w.im.domains.HandlerContext;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.server.ServerType;
import cn.w.im.plugins.MessagePlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-31 下午3:04.
 * Summary: Mongo 序列化插件.
 */
public class MessagePersistentPlugin extends MessagePlugin {

    /**
     * 日志.
     */
    private Log logger = LogFactory.getLog(this.getClass());

    /**
     * 构造函数.
     *
     * @param containerType 服务类型.
     */
    public MessagePersistentPlugin(ServerType containerType) {
        super("MessagePersistentPlugin", "persistent message to mongo db.", containerType);
    }

    @Override
    public boolean isMatch(HandlerContext context) {
        return true;
    }

    @Override
    public void processMessage(Message message, HandlerContext context) {
        MessagePersistentProviderFactory.createProvider(message).save(message);
    }
}
