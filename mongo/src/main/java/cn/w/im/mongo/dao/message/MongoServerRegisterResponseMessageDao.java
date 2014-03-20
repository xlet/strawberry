package cn.w.im.mongo.dao.message;

import cn.w.im.domains.mongo.MongoServerRegisterResponseMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-20 下午3:15.
 * Summary: ServerRegisterResponseMessage mongo dao.
 */
public class MongoServerRegisterResponseMessageDao extends BasicDAO<MongoServerRegisterResponseMessage, ObjectId> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    public MongoServerRegisterResponseMessageDao(Datastore ds) {
        super(ds);
    }
}
