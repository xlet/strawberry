package cn.w.im.persistent.mongo.message.client;

import cn.w.im.domains.messages.client.LoginMessage;
import cn.w.im.domains.mongo.client.MongoLoginMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-3 上午10:20.
 * Summary: MongoLoginMessage Dao.
 */
public class MongoLoginMessageDao extends BasicDAO<MongoLoginMessage, ObjectId> implements MessageDao<LoginMessage> {

    /**
     * 构造函数.
     *
     * @param ds datastore.
     */
    public MongoLoginMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(LoginMessage message) {
        MongoLoginMessage mongoMessage = new MongoLoginMessage(message);
        this.save(mongoMessage);
    }
}
