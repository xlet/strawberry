package cn.w.im.persistent.mongo.dao.message.server;

import cn.w.im.core.providers.persistent.MessagePersistentProvider;
import cn.w.im.core.message.server.ServerRegisterResponseMessage;
import cn.w.im.persistent.mongo.domain.message.server.MongoServerRegisterResponseMessage;
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
@Component(value = "mongoServerRegisterResponseMessagePersistentProvider")
public class MongoServerRegisterResponseMessageDao extends BasicDAO<MongoServerRegisterResponseMessage, ObjectId>
        implements MessagePersistentProvider<ServerRegisterResponseMessage> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    @Autowired
    public MongoServerRegisterResponseMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ServerRegisterResponseMessage message) {
        MongoServerRegisterResponseMessage mongoMessage = new MongoServerRegisterResponseMessage(message);
        this.save(mongoMessage);
    }
}
