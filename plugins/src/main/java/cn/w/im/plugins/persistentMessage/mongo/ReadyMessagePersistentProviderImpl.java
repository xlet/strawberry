package cn.w.im.plugins.persistentMessage.mongo;

import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.ReadyMessage;
import cn.w.im.domains.mongo.MongoReadyMessage;
import cn.w.im.mongo.dao.message.MongoReadyMessageDao;
import cn.w.im.plugins.persistentMessage.MessagePersistentProvider;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-19 下午2:20.
 * Summary: mongo ready message persistent provider implement.
 */
public class ReadyMessagePersistentProviderImpl implements MessagePersistentProvider {

    private MongoReadyMessageDao dao;

    /**
     * constructor.
     *
     * @param dao mongoReadyMessageDao.
     */
    public ReadyMessagePersistentProviderImpl(MongoReadyMessageDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(Message message) {
        ReadyMessage readyMessage = (ReadyMessage) message;
        MongoReadyMessage mongoReadyMessage = new MongoReadyMessage(readyMessage);
        dao.save(mongoReadyMessage);
    }
}
