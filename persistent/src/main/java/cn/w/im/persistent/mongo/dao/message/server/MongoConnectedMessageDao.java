package cn.w.im.persistent.mongo.dao.message.server;

import cn.w.im.core.providers.persistent.MessagePersistentProvider;
import cn.w.im.core.message.server.ConnectedMessage;
import cn.w.im.persistent.mongo.domain.message.server.MongoConnectedMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午6:08.
 * Summary: MongoConnectedMessage dao.
 */
@Component(value = "mongoConnectedMessagePersistentProvider")
public class MongoConnectedMessageDao extends BasicDAO<MongoConnectedMessage, ObjectId> implements MessagePersistentProvider<ConnectedMessage> {
    /**
     * constructor.
     *
     * @param ds data store.
     */
    @Autowired
    public MongoConnectedMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ConnectedMessage message) {
        MongoConnectedMessage mongoMessage = new MongoConnectedMessage(message);
        this.save(mongoMessage);
    }
}
