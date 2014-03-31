package cn.w.im.persistent.mongo.message.client;

import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.domains.mongo.client.MongoNormalMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 下午2:23.
 * Summary: MongoNormalMessageDao.
 */
public class MongoNormalMessageDao extends BasicDAO<MongoNormalMessage, ObjectId> implements MessageDao<NormalMessage> {

    /**
     * 构造函数.
     *
     * @param ds Datastore.
     */
    protected MongoNormalMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(NormalMessage message) {
        MongoNormalMessage mongoMessage = new MongoNormalMessage(message);
        this.save(mongoMessage);
    }
}
