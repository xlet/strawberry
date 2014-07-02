package cn.w.im.persistent.mongo.message.server;

import cn.w.im.domains.messages.server.ForwardMessage;
import cn.w.im.domains.mongo.server.MongoForwardMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午3:01.
 * Summary: MongoForwardMessage Dao.
 */
@Component(value = "mongoForwardMessageDao")
public class MongoForwardMessageDao extends BasicDAO<MongoForwardMessage, ObjectId> implements MessageDao<ForwardMessage> {

    /**
     * 构造函数.
     *
     * @param ds Datastore.
     */
    @Autowired
    public MongoForwardMessageDao(@Qualifier("dataStore")Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ForwardMessage message) {
        MongoForwardMessage mongoMessage = new MongoForwardMessage(message);
        this.save(mongoMessage);
    }
}
