package cn.w.im.mongo.dao.message;

import cn.w.im.domains.mongo.MongoForwardMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午3:01.
 * Summary: MongoForwardMessage Dao.
 */
public class MongoForwardMessageDao extends BasicDAO<MongoForwardMessage, ObjectId> {

    /**
     * 构造函数.
     *
     * @param ds Datastore.
     */
    public MongoForwardMessageDao(Datastore ds) {
        super(ds);
    }
}
