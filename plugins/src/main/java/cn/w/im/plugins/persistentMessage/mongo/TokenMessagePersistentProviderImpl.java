package cn.w.im.plugins.persistentMessage.mongo;

import cn.w.im.domains.messages.Message;
import cn.w.im.domains.messages.TokenMessage;
import cn.w.im.domains.mongo.MongoTokenMessage;
import cn.w.im.mongo.dao.message.MongoTokenMessageDao;
import cn.w.im.plugins.persistentMessage.MessagePersistentProvider;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午4:36.
 * Summary: tokenMessage 持久化实现.
 */
public class TokenMessagePersistentProviderImpl implements MessagePersistentProvider {

    private MongoTokenMessageDao dao;

    /**
     * 构造函数.
     * @param dao mongoTokenMessage dao.
     */
    public TokenMessagePersistentProviderImpl(MongoTokenMessageDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(Message message) {
        TokenMessage tokenMessage = (TokenMessage) message;
        MongoTokenMessage mongoTokenMessage = new MongoTokenMessage(tokenMessage);
        dao.save(mongoTokenMessage);
    }
}
