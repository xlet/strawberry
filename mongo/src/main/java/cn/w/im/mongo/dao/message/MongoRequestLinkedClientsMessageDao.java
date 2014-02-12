package cn.w.im.mongo.dao.message;

import cn.w.im.domains.mongo.MongoRequestLinkedClientsMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-2-11 下午2:52.
 * Summary: MongoRequestLinkedClientsMessage Dao.
 */
public class MongoRequestLinkedClientsMessageDao extends BasicDAO<MongoRequestLinkedClientsMessage,ObjectId> {

    /**
     * 构造函数.
     * @param ds datastore.
     */
    public MongoRequestLinkedClientsMessageDao(Datastore ds) {
        super(ds);
    }
}
