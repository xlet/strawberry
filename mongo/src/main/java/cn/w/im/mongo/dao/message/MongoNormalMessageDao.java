package cn.w.im.mongo.dao.message;

import cn.w.im.domains.mongo.MongoNormalMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 下午2:23.
 * Summary: MongoNormalMessageDao.
 */
public class MongoNormalMessageDao extends BasicDAO<MongoNormalMessage, ObjectId> {

    /**
     * 构造函数.
     * @param ds Datastore.
     */
    protected MongoNormalMessageDao(Datastore ds) {
        super(ds);
    }
}
