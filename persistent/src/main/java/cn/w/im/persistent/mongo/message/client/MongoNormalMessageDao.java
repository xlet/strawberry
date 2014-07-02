package cn.w.im.persistent.mongo.message.client;

import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.domains.mongo.client.MongoNormalMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 下午2:23.
 * Summary: MongoNormalMessageDao.
 */
@Component(value = "mongoNormalMessageDao")
public class MongoNormalMessageDao extends BasicDAO<MongoNormalMessage, ObjectId> implements MessageDao<NormalMessage> {

    /**
     * 构造函数.
     *
     * @param ds Datastore.
     */
    @Autowired
    protected MongoNormalMessageDao(@Qualifier("dataStore")Datastore ds) {
        super(ds);
    }

    @Override
    public void save(NormalMessage message) {
        MongoNormalMessage mongoMessage = new MongoNormalMessage(message);
        this.save(mongoMessage);
    }
}
