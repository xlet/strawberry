package cn.w.im.mongo.dao.message;

import cn.w.im.domains.mongo.MongoResponseLinkedClientsMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-18 下午2:47.
 * Summary:
 */
public class MongoResponseLinkedClientsMessageDao extends BasicDAO<MongoResponseLinkedClientsMessage, ObjectId> {

    /**
     * constructor.
     *
     * @param ds datastore.
     */
    public MongoResponseLinkedClientsMessageDao(Datastore ds) {
        super(ds);
    }
}
