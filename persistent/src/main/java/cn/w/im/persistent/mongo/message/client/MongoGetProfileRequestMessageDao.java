package cn.w.im.persistent.mongo.message.client;

import cn.w.im.domains.messages.client.GetProfileRequestMessage;
import cn.w.im.domains.mongo.client.MongoGetProfileRequestMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * dao of {@link cn.w.im.domains.mongo.client.MongoGetProfileRequestMessage}.
 */
@Component("mongoGetProfileRequestMessageDao")
public class MongoGetProfileRequestMessageDao extends BasicDAO<MongoGetProfileRequestMessage, ObjectId> implements MessageDao<GetProfileRequestMessage> {
    @Autowired
    protected MongoGetProfileRequestMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(GetProfileRequestMessage message) {
        MongoGetProfileRequestMessage mongoMessage = new MongoGetProfileRequestMessage(message);
        this.save(mongoMessage);
    }
}
