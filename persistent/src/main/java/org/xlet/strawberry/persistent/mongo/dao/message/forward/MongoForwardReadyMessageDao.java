package org.xlet.strawberry.persistent.mongo.dao.message.forward;

import org.xlet.strawberry.core.message.persistent.MessagePersistentProvider;
import org.xlet.strawberry.core.message.forward.ForwardReadyMessage;
import org.xlet.strawberry.persistent.mongo.domain.message.forward.MongoForwardReadyMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午5:36.
 * Summary: MongoForwardReadyMessage Dao.
 */
@Component(value = "mongoForwardReadyMessagePersistentProvider")
public class MongoForwardReadyMessageDao extends BasicDAO<MongoForwardReadyMessage, ObjectId>
        implements MessagePersistentProvider<ForwardReadyMessage> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    @Autowired
    public MongoForwardReadyMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ForwardReadyMessage message) {
        MongoForwardReadyMessage mongoMessage = new MongoForwardReadyMessage(message);
        this.save(mongoMessage);
    }
}
