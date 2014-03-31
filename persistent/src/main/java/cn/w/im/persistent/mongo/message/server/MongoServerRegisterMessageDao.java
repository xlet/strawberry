package cn.w.im.persistent.mongo.message.server;

import cn.w.im.domains.messages.server.ServerRegisterMessage;
import cn.w.im.domains.mongo.server.MongoServerRegisterMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 下午4:54.
 * Summary: 消息服务注册消息MongoDao.
 */
public class MongoServerRegisterMessageDao extends BasicDAO<MongoServerRegisterMessage, ObjectId> implements MessageDao<ServerRegisterMessage> {

    /**
     * 构造函数.
     *
     * @param ds datastore.
     */
    public MongoServerRegisterMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ServerRegisterMessage message) {
        MongoServerRegisterMessage mongoMessage = new MongoServerRegisterMessage(message);
        this.save(mongoMessage);
    }
}
