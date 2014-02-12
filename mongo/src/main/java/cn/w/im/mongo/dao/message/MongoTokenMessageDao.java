package cn.w.im.mongo.dao.message;

import cn.w.im.domains.mongo.MongoTokenMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午4:34.
 * Summary: mongoTokenMessage Dao.
 */
public class MongoTokenMessageDao extends BasicDAO<MongoTokenMessage, ObjectId> {

    /**
     * 构造函数.
     *
     * @param ds Datastore.
     */
    public MongoTokenMessageDao(Datastore ds) {
        super(ds);
    }
}
