package org.xlet.strawberry.persistent.mongo.dao.message.server;

import org.xlet.strawberry.core.message.persistent.MessagePersistentProvider;
import org.xlet.strawberry.core.message.server.TokenMessage;
import org.xlet.strawberry.persistent.mongo.domain.message.server.MongoTokenMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午4:34.
 * Summary: mongoTokenMessage Dao.
 */
@Component(value = "mongoTokenMessagePersistentProvider")
public class MongoTokenMessageDao extends BasicDAO<MongoTokenMessage, ObjectId> implements MessagePersistentProvider<TokenMessage> {

    /**
     * 构造函数.
     *
     * @param ds Datastore.
     */
    @Autowired
    public MongoTokenMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(TokenMessage message) {
        MongoTokenMessage mongoMessage = new MongoTokenMessage(message);
        this.save(mongoMessage);
    }
}
