package cn.w.im.plugins.persistentMessage.mongo;

import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.RequestLinkedClientsMessage;
import cn.w.im.domains.mongo.MongoRequestLinkedClientsMessage;
import cn.w.im.mongo.dao.message.MongoRequestLinkedClientsMessageDao;
import cn.w.im.plugins.persistentMessage.MessagePersistentProvider;

/**
 * Creator: JackieHan.
 * DateTime: 14-2-11 下午2:55.
 * Summary:
 */
public class RequestLinkedClientsMessagePersistentProviderImpl implements MessagePersistentProvider {

    private MongoRequestLinkedClientsMessageDao dao;

    /**
     * 构造函数.
     *
     * @param dao MongoRequestLinkedClientsMessage Dao.
     */
    public RequestLinkedClientsMessagePersistentProviderImpl(MongoRequestLinkedClientsMessageDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(Message message) {
        RequestLinkedClientsMessage requestLinkedClientsMessage = (RequestLinkedClientsMessage) message;
        MongoRequestLinkedClientsMessage mongoMessage = new MongoRequestLinkedClientsMessage(requestLinkedClientsMessage);
        dao.save(mongoMessage);
    }
}
