package cn.w.im.mongo.dao.message.forward;

import cn.w.im.domains.messages.forward.ForwardReadyMessage;
import cn.w.im.domains.mongo.forward.MongoForwardReadyMessage;
import cn.w.im.mongo.dao.message.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午5:36.
 * Summary: MongoForwardReadyMessage Dao.
 */
public class MongoForwardReadyMessageDao extends BasicDAO<MongoForwardReadyMessage, ObjectId> implements MessageDao<ForwardReadyMessage> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    public MongoForwardReadyMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ForwardReadyMessage message) {
        MongoForwardReadyMessage mongoMessage = new MongoForwardReadyMessage(message);
        this.save(mongoMessage);
    }
}
