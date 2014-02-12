package cn.w.im.plugins.persistentMessage.mongo;

import cn.w.im.domains.messages.ConnectMessage;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.mongo.MongoConnectMessage;
import cn.w.im.mongo.dao.message.MongoConnectMessageDao;
import cn.w.im.plugins.persistentMessage.MessagePersistentProvider;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午2:38.
 * Summary: ConnectMessage mongo 持久化实现.
 */
public class ConnectMessagePersistentProviderImpl implements MessagePersistentProvider {

    private MongoConnectMessageDao dao;

    /**
     * 构造函数.
     * @param dao mongoConnectMessageDao.
     */
    public ConnectMessagePersistentProviderImpl(MongoConnectMessageDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(Message message) {
        ConnectMessage connectMessage = (ConnectMessage) message;
        MongoConnectMessage mongoConnectMessage = new MongoConnectMessage(connectMessage);
        dao.save(mongoConnectMessage);
    }
}
