package cn.w.im.persistent.mongo.message.server;

import cn.w.im.domains.messages.server.ServerRegisterResponseMessage;
import cn.w.im.domains.mongo.server.MongoServerRegisterResponseMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-20 下午3:15.
 * Summary: ServerRegisterResponseMessage mongo dao.
 */
@Component(value = "mongoServerRegisterResponseMessageDao")
public class MongoServerRegisterResponseMessageDao extends BasicDAO<MongoServerRegisterResponseMessage, ObjectId> implements MessageDao<ServerRegisterResponseMessage> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    @Autowired
    public MongoServerRegisterResponseMessageDao(@Qualifier("dataStore")Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ServerRegisterResponseMessage message) {
        MongoServerRegisterResponseMessage mongoMessage = new MongoServerRegisterResponseMessage(message);
        this.save(mongoMessage);
    }
}
