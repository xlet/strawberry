package cn.w.im.mongo.dao.message.forward;

import cn.w.im.domains.messages.forward.ForwardRequestMessage;
import cn.w.im.domains.mongo.forward.MongoForwardRequestMessage;
import cn.w.im.mongo.dao.message.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午6:05.
 * Summary: MongoForwardRequestMessage dao.
 */
public class MongoForwardRequestMessageDao extends BasicDAO<MongoForwardRequestMessage, ObjectId> implements MessageDao<ForwardRequestMessage> {
    /**
     * constructor.
     *
     * @param ds data store.
     */
    public MongoForwardRequestMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ForwardRequestMessage message) {
        MongoForwardRequestMessage mongoMessage = new MongoForwardRequestMessage(message);
        this.save(mongoMessage);
    }
}
