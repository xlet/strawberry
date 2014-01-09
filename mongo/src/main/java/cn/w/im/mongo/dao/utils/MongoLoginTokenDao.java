package cn.w.im.mongo.dao.utils;

import cn.w.im.domains.mongo.MongoLoginToken;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-8 下午5:31.
 * Summary:
 */
public class MongoLoginTokenDao extends BasicDAO<MongoLoginToken, ObjectId> {

    /**
     * 构造函数.
     * @param ds Datastore.
     */
    public MongoLoginTokenDao(Datastore ds) {
        super(ds);
    }
}
