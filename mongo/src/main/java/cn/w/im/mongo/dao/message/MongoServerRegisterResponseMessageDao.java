package cn.w.im.mongo.dao.message;

import cn.w.im.domains.messages.responses.ServerRegisterResponseMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-20 下午3:15.
 * Summary: ServerRegisterResponseMessage mongo dao.
 */
public class MongoServerRegisterResponseMessageDao extends BasicDAO<ServerRegisterResponseMessage, ObjectId> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    public MongoServerRegisterResponseMessageDao(Datastore ds) {
        super(ds);
    }
}
