package cn.w.im.persistent.mongo.dao.message.server;

import cn.w.im.core.message.persistent.MessagePersistentProvider;
import cn.w.im.core.message.server.ServerRegisterMessage;
import cn.w.im.persistent.mongo.domain.message.server.MongoServerRegisterMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 下午4:54.
 * Summary: 消息服务注册消息MongoDao.
 */
@Component(value = "mongoServerRegisterMessagePersistentProvider")
public class MongoServerRegisterMessageDao extends BasicDAO<MongoServerRegisterMessage, ObjectId>
        implements MessagePersistentProvider<ServerRegisterMessage> {

    /**
     * 构造函数.
     *
     * @param ds datastore.
     */
    @Autowired
    public MongoServerRegisterMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ServerRegisterMessage message) {
        MongoServerRegisterMessage mongoMessage = new MongoServerRegisterMessage(message);
        this.save(mongoMessage);
    }
}
