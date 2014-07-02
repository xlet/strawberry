package cn.w.im.persistent.mongo.message.server;

import cn.w.im.domains.messages.server.ConnectedResponseMessage;
import cn.w.im.domains.mongo.server.MongoConnectedResponseMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午6:09.
 * Summary: MongoConnectedResponseMessage dao.
 */
@Component(value = "mongoConnectedResponseMessageDao")
public class MongoConnectedResponseMessageDao extends BasicDAO<MongoConnectedResponseMessage, ObjectId> implements MessageDao<ConnectedResponseMessage> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    @Autowired
    public MongoConnectedResponseMessageDao(@Qualifier("dataStore")Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ConnectedResponseMessage message) {
        MongoConnectedResponseMessage mongoMessage = new MongoConnectedResponseMessage(message);
        this.save(mongoMessage);
    }
}
