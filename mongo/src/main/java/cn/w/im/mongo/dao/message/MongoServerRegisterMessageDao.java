package cn.w.im.mongo.dao.message;

import cn.w.im.domains.mongo.MongoServerRegisterMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 下午4:54.
 * Summary: 消息服务注册消息MongoDao.
 */
public class MongoServerRegisterMessageDao extends BasicDAO<MongoServerRegisterMessage, ObjectId> {

    /**
     * 构造函数.
     * @param ds datastore.
     */
    public MongoServerRegisterMessageDao(Datastore ds) {
        super(ds);
    }
}
