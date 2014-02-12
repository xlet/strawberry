package cn.w.im.plugins.persistentMessage.mongo;

import cn.w.im.domains.messages.ForwardMessage;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.mongo.MongoForwardMessage;
import cn.w.im.mongo.dao.message.MongoForwardMessageDao;
import cn.w.im.plugins.persistentMessage.MessagePersistentProvider;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午3:03.
 * Summary: innerForwardMessage 持久化实现.
 */
public class ForwardMessagePersistentProviderImpl implements MessagePersistentProvider {

    private MongoForwardMessageDao dao;

    /**
     * 构造函数.
     * @param dao forwardMessageDao.
     */
    public ForwardMessagePersistentProviderImpl(MongoForwardMessageDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(Message message) {
        ForwardMessage forwardMessage = (ForwardMessage) message;
        MongoForwardMessage mongoForwardMessage = new MongoForwardMessage(forwardMessage);
        dao.save(mongoForwardMessage);
    }
}
