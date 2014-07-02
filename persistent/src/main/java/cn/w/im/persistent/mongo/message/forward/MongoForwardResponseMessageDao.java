package cn.w.im.persistent.mongo.message.forward;

import cn.w.im.domains.messages.forward.ForwardResponseMessage;
import cn.w.im.domains.mongo.forward.MongoForwardResponseMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午6:07.
 * Summary: MongoForwardResponseMessage dao.
 */
@Component(value = "mongoForwardResponseMessageDao")
public class MongoForwardResponseMessageDao extends BasicDAO<MongoForwardResponseMessage, ObjectId> implements MessageDao<ForwardResponseMessage> {
    /**
     * constructor.
     *
     * @param ds data store.
     */
    @Autowired
    public MongoForwardResponseMessageDao(@Qualifier("dataStore")Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ForwardResponseMessage message) {
        MongoForwardResponseMessage mongoMessage = new MongoForwardResponseMessage(message);
        this.save(mongoMessage);
    }
}
