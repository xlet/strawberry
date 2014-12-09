package org.xlet.strawberry.persistent.mongo.dao.message.client;

import org.xlet.strawberry.core.message.persistent.MessagePersistentProvider;
import org.xlet.strawberry.core.message.client.LogoutMessage;
import org.xlet.strawberry.persistent.mongo.domain.message.client.MongoLogoutMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 上午10:25.
 * Summary: MongoLogoutMessageDao.
 */
@Component(value = "mongoLogoutMessagePersistentProvider")
public class MongoLogoutMessageDao extends BasicDAO<MongoLogoutMessage, ObjectId> implements MessagePersistentProvider<LogoutMessage> {

    /**
     * 构造函数.
     *
     * @param ds Datastore.
     */
    @Autowired
    protected MongoLogoutMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(LogoutMessage message) {
        MongoLogoutMessage mongoMessage = new MongoLogoutMessage(message);
        this.save(mongoMessage);
    }
}
