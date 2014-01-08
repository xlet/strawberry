package cn.w.im.plugins.mongo.message.dao;

import cn.w.im.domains.mongo.MongoLoginMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-3 上午10:20.
 * Summary: MongoLoginMessage Dao.
 */
public class MongoLoginMessageDao extends BasicDAO<MongoLoginMessage, ObjectId> {

    /**
     * 构造函数.
     * @param ds datastore.
     */
    public MongoLoginMessageDao(Datastore ds) {
        super(ds);
    }
}
