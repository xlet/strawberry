package cn.w.im.plugins.persistentMessage;

import cn.w.im.domains.messages.Message;
import cn.w.im.exceptions.NotSupportMessageTypeException;
import cn.w.im.exceptions.NotSupportedDataStoreException;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午7:42.
 * Summary: PersistentMessageProvider factory.
 */
public class MessagePersistentProviderFactory {

    /**
     * create message persistent provider implement.
     * @param message message.
     * @param dataStoreType data store type.
     * @param <TMessage> message type.
     * @return message persistent provider implement.
     * @throws NotSupportMessageTypeException message type is not supported.
     * @throws NotSupportedDataStoreException data store is not supported.
     */
    public static <TMessage extends Message> MessagePersistentProvider<TMessage> create(TMessage message, String dataStoreType) throws NotSupportMessageTypeException, NotSupportedDataStoreException {
        if (dataStoreType.equals("Mongo")) {
            return new MongoMessagePersistentProviderImpl(message.getMessageType());
        }
        throw new NotSupportedDataStoreException(dataStoreType);
    }
}
