package cn.w.im.persistent.mongo.message.server;

import cn.w.im.domains.messages.server.ReadyMessage;
import cn.w.im.domains.mongo.server.MongoReadyMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-19 下午2:07.
 * Summary: mongo ready message dao.
 */
@Component(value = "mongoReadyMessageDao")
public class MongoReadyMessageDao extends BasicDAO<MongoReadyMessage, ObjectId> implements MessageDao<ReadyMessage> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    @Autowired
    public MongoReadyMessageDao(@Qualifier("dataStore")Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ReadyMessage message) {
        MongoReadyMessage mongoMessage = new MongoReadyMessage(message);
        this.save(mongoMessage);
    }
}
