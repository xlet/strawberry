package cn.w.im.plugins.mongo.message.dao;

import cn.w.im.domains.mongo.MongoLogoutMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 上午10:25.
 * Summary: MongoLogoutMessageDao.
 */
public class MongoLogoutMessageDao extends BasicDAO<MongoLogoutMessage, ObjectId> {

    /**
     * 构造函数.
     * @param ds Datastore.
     */
    protected MongoLogoutMessageDao(Datastore ds) {
        super(ds);
    }
}
