package cn.w.im.mongo.dao.message.server;

import cn.w.im.domains.messages.server.ForwardMessage;
import cn.w.im.domains.mongo.server.MongoForwardMessage;
import cn.w.im.mongo.dao.message.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午3:01.
 * Summary: MongoForwardMessage Dao.
 */
public class MongoForwardMessageDao extends BasicDAO<MongoForwardMessage, ObjectId> implements MessageDao<ForwardMessage> {

    /**
     * 构造函数.
     *
     * @param ds Datastore.
     */
    public MongoForwardMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ForwardMessage message) {
        MongoForwardMessage mongoMessage = new MongoForwardMessage(message);
        this.save(mongoMessage);
    }
}
