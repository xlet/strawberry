package cn.w.im.plugins.persistentMessage.mongo;

import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.ServerRegisterMessage;
import cn.w.im.domains.mongo.MongoServerRegisterMessage;
import cn.w.im.mongo.dao.message.MongoServerRegisterMessageDao;
import cn.w.im.plugins.persistentMessage.MessagePersistentProvider;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 下午4:32.
 * Summary:
 */
public class ServerRegisterMessagePersistentProviderImpl implements MessagePersistentProvider {

    private MongoServerRegisterMessageDao dao;

    /**
     * 构造函数.
     * @param dao 消息函数
     */
    public ServerRegisterMessagePersistentProviderImpl(MongoServerRegisterMessageDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(Message message) {
        ServerRegisterMessage registerMessage = (ServerRegisterMessage) message;
        MongoServerRegisterMessage mongoRegisterMessage = new MongoServerRegisterMessage(registerMessage);
        dao.save(mongoRegisterMessage);
    }
}
