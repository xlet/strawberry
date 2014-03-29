package cn.w.im.mongo.dao.message.forward;

import cn.w.im.domains.messages.forward.ForwardResponseMessage;
import cn.w.im.domains.mongo.forward.MongoForwardResponseMessage;
import cn.w.im.mongo.dao.message.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午6:07.
 * Summary: MongoForwardResponseMessage dao.
 */
public class MongoForwardResponseMessageDao extends BasicDAO<MongoForwardResponseMessage, ObjectId> implements MessageDao<ForwardResponseMessage> {
    /**
     * constructor.
     *
     * @param ds data store.
     */
    public MongoForwardResponseMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ForwardResponseMessage message) {
        MongoForwardResponseMessage mongoMessage = new MongoForwardResponseMessage(message);
        this.save(mongoMessage);
    }
}
