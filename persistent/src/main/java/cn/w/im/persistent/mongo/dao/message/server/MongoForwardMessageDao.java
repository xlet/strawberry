package cn.w.im.persistent.mongo.dao.message.server;

import cn.w.im.core.providers.persistent.MessagePersistentProvider;
import cn.w.im.core.message.server.ForwardMessage;
import cn.w.im.persistent.mongo.domain.message.server.MongoForwardMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午3:01.
 * Summary: MongoForwardMessage Dao.
 */
@Component(value = "mongoForwardMessagePersistentProvider")
public class MongoForwardMessageDao extends BasicDAO<MongoForwardMessage, ObjectId> implements MessagePersistentProvider<ForwardMessage> {

    /**
     * 构造函数.
     *
     * @param ds Datastore.
     */
    @Autowired
    public MongoForwardMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ForwardMessage message) {
        MongoForwardMessage mongoMessage = new MongoForwardMessage(message);
        this.save(mongoMessage);
    }
}
