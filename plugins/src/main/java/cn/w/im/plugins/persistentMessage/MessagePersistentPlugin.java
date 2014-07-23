package cn.w.im.plugins.persistentMessage;

import cn.w.im.domains.PluginContext;
import cn.w.im.domains.ServerType;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.client.GetProfileRequestMessage;
import cn.w.im.exceptions.ServerInnerException;
import cn.w.im.persistent.MessageDao;
import cn.w.im.persistent.PersistentRepositoryFactory;
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
    private final Log logger = LogFactory.getLog(this.getClass());

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
        //TODO does all messages need to be saved?
        return true;
    }

    @Override
    public void processMessage(TMessage message, PluginContext context) {
        try {
            if (!(message instanceof GetProfileRequestMessage)) {
                MessageDao messageDao = PersistentRepositoryFactory.getMessageDao(message);
                messageDao.save(message);
            }
        } catch (ServerInnerException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
