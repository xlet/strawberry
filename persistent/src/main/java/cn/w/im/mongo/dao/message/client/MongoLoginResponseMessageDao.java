package cn.w.im.mongo.dao.message.client;

import cn.w.im.domains.messages.client.LoginResponseMessage;
import cn.w.im.domains.mongo.client.MongoLoginResponseMessage;
import cn.w.im.mongo.dao.message.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午5:28.
 * Summary: MongoLoginResponseMessage Dao.
 */
public class MongoLoginResponseMessageDao extends BasicDAO<MongoLoginResponseMessage, ObjectId> implements MessageDao<LoginResponseMessage> {
    /**
     * constructor.
     *
     * @param ds data store.
     */
    public MongoLoginResponseMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(LoginResponseMessage message) {
        MongoLoginResponseMessage mongoMessage = new MongoLoginResponseMessage(message);
        this.save(mongoMessage);
    }
}
