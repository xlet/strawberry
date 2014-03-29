package cn.w.im.mongo.dao.message.client;

import cn.w.im.domains.messages.client.ConnectResponseMessage;
import cn.w.im.domains.mongo.client.MongoConnectResponseMessage;
import cn.w.im.mongo.dao.message.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午5:27.
 * Summary: MongoConnectResponseMessage dao.
 */
public class MongoConnectResponseMessageDao extends BasicDAO<MongoConnectResponseMessage, ObjectId> implements MessageDao<ConnectResponseMessage> {
    /**
     * constructor.
     *
     * @param ds data store.
     */
    public MongoConnectResponseMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ConnectResponseMessage message) {
        MongoConnectResponseMessage mongoMessage = new MongoConnectResponseMessage(message);
        this.save(mongoMessage);
    }
}
