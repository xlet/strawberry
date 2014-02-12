package cn.w.im.mongo.dao.message;

import cn.w.im.domains.mongo.MongoConnectMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午2:34.
 * Summary: mongoConnectMessage Dao.
 */
public class MongoConnectMessageDao extends BasicDAO<MongoConnectMessage, ObjectId> {

    /**
     * 构造函数.
     *
     * @param ds Datastore.
     */
    protected MongoConnectMessageDao(Datastore ds) {
        super(ds);
    }
}
