package cn.w.im.mongo.dao.message.server;

import cn.w.im.domains.messages.server.ConnectedResponseMessage;
import cn.w.im.domains.mongo.server.MongoConnectedResponseMessage;
import cn.w.im.mongo.dao.message.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午6:09.
 * Summary: MongoConnectedResponseMessage dao.
 */
public class MongoConnectedResponseMessageDao extends BasicDAO<MongoConnectedResponseMessage, ObjectId> implements MessageDao<ConnectedResponseMessage> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    public MongoConnectedResponseMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ConnectedResponseMessage message) {
        MongoConnectedResponseMessage mongoMessage = new MongoConnectedResponseMessage(message);
        this.save(mongoMessage);
    }
}
