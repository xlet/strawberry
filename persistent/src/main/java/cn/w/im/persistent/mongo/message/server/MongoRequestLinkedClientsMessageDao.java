package cn.w.im.persistent.mongo.message.server;

import cn.w.im.domains.messages.server.RequestLinkedClientsMessage;
import cn.w.im.domains.mongo.server.MongoRequestLinkedClientsMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-2-11 下午2:52.
 * Summary: MongoRequestLinkedClientsMessage Dao.
 */
@Component(value = "mongoRequestLinkedClientsMessageDao")
public class MongoRequestLinkedClientsMessageDao extends BasicDAO<MongoRequestLinkedClientsMessage, ObjectId> implements MessageDao<RequestLinkedClientsMessage> {

    /**
     * 构造函数.
     *
     * @param ds datastore.
     */
    @Autowired
    public MongoRequestLinkedClientsMessageDao(@Qualifier("dataStore")Datastore ds) {
        super(ds);
    }

    @Override
    public void save(RequestLinkedClientsMessage message) {
        MongoRequestLinkedClientsMessage mongoMessage = new MongoRequestLinkedClientsMessage(message);
        this.save(mongoMessage);
    }
}
