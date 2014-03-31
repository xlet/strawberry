package cn.w.im.persistent.mongo.message.client;

import cn.w.im.domains.messages.client.ConnectMessage;
import cn.w.im.domains.mongo.client.MongoConnectMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午2:34.
 * Summary: mongoConnectMessage Dao.
 */
public class MongoConnectMessageDao extends BasicDAO<MongoConnectMessage, ObjectId> implements MessageDao<ConnectMessage> {

    /**
     * 构造函数.
     *
     * @param ds Datastore.
     */
    protected MongoConnectMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ConnectMessage message) {
        MongoConnectMessage mongoMessage = new MongoConnectMessage(message);
        this.save(mongoMessage);
    }
}
