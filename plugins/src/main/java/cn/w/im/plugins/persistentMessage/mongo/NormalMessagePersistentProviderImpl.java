package cn.w.im.plugins.persistentMessage.mongo;

import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.NormalMessage;
import cn.w.im.domains.mongo.MongoNormalMessage;
import cn.w.im.mongo.dao.message.MongoNormalMessageDao;
import cn.w.im.plugins.persistentMessage.MessagePersistentProvider;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 下午3:03.
 * Summary: MongoNoramlMessage 序列化实现.
 */
public class NormalMessagePersistentProviderImpl implements MessagePersistentProvider {

    /**
     * MongoNormalMessageDao.
     */
    private MongoNormalMessageDao dao;

    /**
     * 构造函数.
     * @param dao MongoNormalMessageDao.
     */
    public NormalMessagePersistentProviderImpl(MongoNormalMessageDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(Message message) {
        NormalMessage normalMessage = (NormalMessage) message;
        MongoNormalMessage mongoNormalMessage = new MongoNormalMessage(normalMessage);
        dao.save(mongoNormalMessage);
    }
}
