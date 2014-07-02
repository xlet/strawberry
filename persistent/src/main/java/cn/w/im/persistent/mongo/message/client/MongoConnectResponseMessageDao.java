package cn.w.im.persistent.mongo.message.client;

import cn.w.im.domains.messages.client.ConnectResponseMessage;
import cn.w.im.domains.mongo.client.MongoConnectResponseMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午5:27.
 * Summary: MongoConnectResponseMessage dao.
 */
@Component(value = "mongoConnectResponseMessageDao")
public class MongoConnectResponseMessageDao extends BasicDAO<MongoConnectResponseMessage, ObjectId> implements MessageDao<ConnectResponseMessage> {
    /**
     * constructor.
     *
     * @param ds data store.
     */
    @Autowired
    public MongoConnectResponseMessageDao(@Qualifier("dataStore")Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ConnectResponseMessage message) {
        MongoConnectResponseMessage mongoMessage = new MongoConnectResponseMessage(message);
        this.save(mongoMessage);
    }
}
