package cn.w.im.plugins.persistentMessage;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.Message;
import cn.w.im.exceptions.NotSupportMessageTypeException;
import cn.w.im.mongo.dao.message.MessageDao;
import cn.w.im.utils.spring.SpringContext;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午7:14.
 * Summary: mongo MessagePersistentProvider implement.
 */
public class MongoMessagePersistentProviderImpl<TMessage extends Message> implements MessagePersistentProvider<TMessage> {

    private MessageDao<TMessage> dao;

    /**
     * constructor.
     *
     * @param messageType message type.
     * @throws NotSupportMessageTypeException message type is not supported.
     */
    public MongoMessagePersistentProviderImpl(MessageType messageType) throws NotSupportMessageTypeException {
        dao = (MessageDao) SpringContext.context().getBean("Mongo" + messageType.toString() + "Dao");
        if (dao == null) {
            throw new NotSupportMessageTypeException(messageType);
        }
    }

    @Override
    public void save(TMessage message) {
        dao.save(message);
    }
}
