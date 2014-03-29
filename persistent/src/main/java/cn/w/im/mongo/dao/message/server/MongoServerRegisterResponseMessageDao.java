package cn.w.im.mongo.dao.message.server;

import cn.w.im.domains.messages.server.ServerRegisterResponseMessage;
import cn.w.im.domains.mongo.server.MongoServerRegisterResponseMessage;
import cn.w.im.mongo.dao.message.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-20 下午3:15.
 * Summary: ServerRegisterResponseMessage mongo dao.
 */
public class MongoServerRegisterResponseMessageDao extends BasicDAO<MongoServerRegisterResponseMessage, ObjectId> implements MessageDao<ServerRegisterResponseMessage> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    public MongoServerRegisterResponseMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ServerRegisterResponseMessage message) {
        MongoServerRegisterResponseMessage mongoMessage = new MongoServerRegisterResponseMessage(message);
        this.save(mongoMessage);
    }
}
