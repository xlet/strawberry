package cn.w.im.persistent.mongo.dao.message.client;

import cn.w.im.core.providers.persistent.MessagePersistentProvider;
import cn.w.im.core.message.client.LogoutResponseMessage;
import cn.w.im.persistent.mongo.domain.message.client.MongoLogoutResponseMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午5:31.
 * Summary: MongoLogoutResponseMessage Dao.
 */
@Component(value = "mongoLogoutResponseMessagePersistentProvider")
public class MongoLogoutResponseMessageDao extends BasicDAO<MongoLogoutResponseMessage, ObjectId> implements MessagePersistentProvider<LogoutResponseMessage> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    @Autowired
    protected MongoLogoutResponseMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(LogoutResponseMessage message) {
        MongoLogoutResponseMessage mongoMessage = new MongoLogoutResponseMessage(message);
        this.save(mongoMessage);
    }
}
