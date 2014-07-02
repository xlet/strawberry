package cn.w.im.persistent.mongo.message.client;

import cn.w.im.domains.messages.client.LogoutResponseMessage;
import cn.w.im.domains.mongo.client.MongoLogoutResponseMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午5:31.
 * Summary: MongoLogoutResponseMessage Dao.
 */
@Component(value = "mongoLogoutResponseMessageDao")
public class MongoLogoutResponseMessageDao extends BasicDAO<MongoLogoutResponseMessage, ObjectId> implements MessageDao<LogoutResponseMessage> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    @Autowired
    protected MongoLogoutResponseMessageDao(@Qualifier("dataStore")Datastore ds) {
        super(ds);
    }

    @Override
    public void save(LogoutResponseMessage message) {
        MongoLogoutResponseMessage mongoMessage = new MongoLogoutResponseMessage(message);
        this.save(mongoMessage);
    }
}
