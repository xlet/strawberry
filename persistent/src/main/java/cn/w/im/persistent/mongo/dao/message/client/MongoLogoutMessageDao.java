package cn.w.im.persistent.mongo.dao.message.client;

import cn.w.im.core.message.persistent.MessagePersistentProvider;
import cn.w.im.core.message.client.LogoutMessage;
import cn.w.im.persistent.mongo.domain.message.client.MongoLogoutMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 上午10:25.
 * Summary: MongoLogoutMessageDao.
 */
@Component(value = "mongoLogoutMessagePersistentProvider")
public class MongoLogoutMessageDao extends BasicDAO<MongoLogoutMessage, ObjectId> implements MessagePersistentProvider<LogoutMessage> {

    /**
     * 构造函数.
     *
     * @param ds Datastore.
     */
    @Autowired
    protected MongoLogoutMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(LogoutMessage message) {
        MongoLogoutMessage mongoMessage = new MongoLogoutMessage(message);
        this.save(mongoMessage);
    }
}
