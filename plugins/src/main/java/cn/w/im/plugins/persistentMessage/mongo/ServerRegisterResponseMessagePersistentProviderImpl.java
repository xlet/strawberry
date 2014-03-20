package cn.w.im.plugins.persistentMessage.mongo;

import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.responses.ServerRegisterResponseMessage;
import cn.w.im.domains.mongo.MongoServerRegisterResponseMessage;
import cn.w.im.mongo.dao.message.MongoServerRegisterResponseMessageDao;
import cn.w.im.plugins.persistentMessage.MessagePersistentProvider;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-20 下午3:18.
 * Summary: ServerRegisterResponseMessage persistent provider implement.
 */
public class ServerRegisterResponseMessagePersistentProviderImpl implements MessagePersistentProvider {

    private MongoServerRegisterResponseMessageDao dao;

    /**
     * constructor.
     * @param dao ServerRegisterResponseMessage Mongo Dao.
     */
    public ServerRegisterResponseMessagePersistentProviderImpl(MongoServerRegisterResponseMessageDao dao) {
        this.dao = dao;
    }
        @Override
        public void save(Message message) {
            ServerRegisterResponseMessage responseMessage = (ServerRegisterResponseMessage) message;
            MongoServerRegisterResponseMessage mongoMessage = new MongoServerRegisterResponseMessage(responseMessage);
            dao.save(mongoMessage);
        }
}
