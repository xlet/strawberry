package org.xlet.strawberry.persistent.mongo.dao.message.client;

import org.xlet.strawberry.core.message.persistent.MessagePersistentProvider;
import org.xlet.strawberry.core.message.client.LoginResponseMessage;
import org.xlet.strawberry.persistent.mongo.domain.message.client.MongoLoginResponseMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午5:28.
 * Summary: MongoLoginResponseMessage Dao.
 */
@Component(value = "mongoLoginResponseMessagePersistentProvider")
public class MongoLoginResponseMessageDao extends BasicDAO<MongoLoginResponseMessage, ObjectId> implements MessagePersistentProvider<LoginResponseMessage> {
    /**
     * constructor.
     *
     * @param ds data store.
     */
    @Autowired
    public MongoLoginResponseMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(LoginResponseMessage message) {
        MongoLoginResponseMessage mongoMessage = new MongoLoginResponseMessage(message);
        this.save(mongoMessage);
    }
}
