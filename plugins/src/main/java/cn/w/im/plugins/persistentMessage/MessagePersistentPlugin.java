package cn.w.im.plugins.persistentMessage;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.conf.Configuration;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.ServerType;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.plugins.MessagePlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-31 下午3:04.
 * Summary: message persistent plugin.
 */
public class MessagePersistentPlugin<TMessage extends Message> extends MessagePlugin<TMessage> {

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
    public boolean isMatch(PluginContext context) {
        return true;
    }

    @Override
    public void processMessage(TMessage message, PluginContext context) {
        try {
            MessagePersistentProvider provider = MessagePersistentProviderFactory.create(message, Configuration.current().getDataStoreType());
            provider.save(message);
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
