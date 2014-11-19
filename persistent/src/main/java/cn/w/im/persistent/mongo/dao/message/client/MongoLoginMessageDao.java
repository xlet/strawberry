package cn.w.im.persistent.mongo.dao.message.client;

import cn.w.im.core.message.persistent.MessagePersistentProvider;
import cn.w.im.core.message.client.LoginMessage;
import cn.w.im.persistent.mongo.domain.message.client.MongoLoginMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-3 上午10:20.
 * Summary: MongoLoginMessage Dao.
 */
@Component(value = "mongoLoginMessagePersistentProvider")
public class MongoLoginMessageDao extends BasicDAO<MongoLoginMessage, ObjectId> implements MessagePersistentProvider<LoginMessage> {

    /**
     * 构造函数.
     *
     * @param ds datastore.
     */
    @Autowired
    public MongoLoginMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(LoginMessage message) {
        MongoLoginMessage mongoMessage = new MongoLoginMessage(message);
        this.save(mongoMessage);
    }
}
