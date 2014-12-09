package org.xlet.strawberry.persistent.mongo.dao.message.server;

import org.xlet.strawberry.core.message.persistent.MessagePersistentProvider;
import org.xlet.strawberry.core.message.server.ConnectedResponseMessage;
import org.xlet.strawberry.persistent.mongo.domain.message.server.MongoConnectedResponseMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午6:09.
 * Summary: MongoConnectedResponseMessage dao.
 */
@Component(value = "mongoConnectedResponseMessagePersistentProvider")
public class MongoConnectedResponseMessageDao extends BasicDAO<MongoConnectedResponseMessage, ObjectId>
        implements MessagePersistentProvider<ConnectedResponseMessage> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    @Autowired
    public MongoConnectedResponseMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ConnectedResponseMessage message) {
        MongoConnectedResponseMessage mongoMessage = new MongoConnectedResponseMessage(message);
        this.save(mongoMessage);
    }
}
