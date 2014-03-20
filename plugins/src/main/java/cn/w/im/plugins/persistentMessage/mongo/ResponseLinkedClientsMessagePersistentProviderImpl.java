package cn.w.im.plugins.persistentMessage.mongo;

import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.responses.ResponseLinkedClientsMessage;
import cn.w.im.domains.mongo.MongoResponseLinkedClientsMessage;
import cn.w.im.mongo.dao.message.MongoResponseLinkedClientsMessageDao;
import cn.w.im.plugins.persistentMessage.MessagePersistentProvider;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-18 下午2:51.
 * Summary:
 */
public class ResponseLinkedClientsMessagePersistentProviderImpl implements MessagePersistentProvider {

    private MongoResponseLinkedClientsMessageDao dao;

    /**
     * constructor.
     * @param dao mongo ResponseLinkedClientMessage dao.
     */
    public ResponseLinkedClientsMessagePersistentProviderImpl(MongoResponseLinkedClientsMessageDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(Message message) {
        ResponseLinkedClientsMessage responseMessage = (ResponseLinkedClientsMessage) message;
        MongoResponseLinkedClientsMessage mongoMessage = new MongoResponseLinkedClientsMessage(responseMessage);
        dao.save(mongoMessage);
    }
}
