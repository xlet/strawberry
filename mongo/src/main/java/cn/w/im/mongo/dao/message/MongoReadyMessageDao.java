package cn.w.im.mongo.dao.message;

import cn.w.im.domains.mongo.MongoReadyMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-19 下午2:07.
 * Summary: mongo ready message dao.
 */
public class MongoReadyMessageDao extends BasicDAO<MongoReadyMessage, ObjectId> {

    /**
     * constructor.
     *
     * @param ds data store.
     */
    public MongoReadyMessageDao(Datastore ds) {
        super(ds);
    }
}
