package cn.w.im.persistent.mongo.message.server;

import cn.w.im.domains.messages.server.ConnectedMessage;
import cn.w.im.domains.mongo.server.MongoConnectedMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午6:08.
 * Summary: MongoConnectedMessage dao.
 */
@Component(value = "mongoConnectedMessageDao")
public class MongoConnectedMessageDao extends BasicDAO<MongoConnectedMessage, ObjectId> implements MessageDao<ConnectedMessage> {
    /**
     * constructor.
     *
     * @param ds data store.
     */
    @Autowired
    public MongoConnectedMessageDao(@Qualifier("dataStore")Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ConnectedMessage message) {
        MongoConnectedMessage mongoMessage = new MongoConnectedMessage(message);
        this.save(mongoMessage);
    }
}
