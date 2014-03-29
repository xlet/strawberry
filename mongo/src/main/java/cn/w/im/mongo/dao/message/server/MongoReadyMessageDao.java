package cn.w.im.mongo.dao.message.server;

import cn.w.im.domains.messages.server.ReadyMessage;
import cn.w.im.domains.mongo.server.MongoReadyMessage;
import cn.w.im.mongo.dao.message.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-19 下午2:07.
 * Summary: mongo ready message dao.
 */
public class MongoReadyMessageDao extends BasicDAO<MongoReadyMessage, ObjectId> implements MessageDao<ReadyMessage> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    public MongoReadyMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ReadyMessage message) {
        MongoReadyMessage mongoMessage = new MongoReadyMessage(message);
        this.save(mongoMessage);
    }
}
