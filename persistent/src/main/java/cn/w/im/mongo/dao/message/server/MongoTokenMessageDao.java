package cn.w.im.mongo.dao.message.server;

import cn.w.im.domains.messages.server.TokenMessage;
import cn.w.im.domains.mongo.server.MongoTokenMessage;
import cn.w.im.mongo.dao.message.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午4:34.
 * Summary: mongoTokenMessage Dao.
 */
public class MongoTokenMessageDao extends BasicDAO<MongoTokenMessage, ObjectId> implements MessageDao<TokenMessage> {

    /**
     * 构造函数.
     *
     * @param ds Datastore.
     */
    public MongoTokenMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(TokenMessage message) {
        MongoTokenMessage mongoMessage = new MongoTokenMessage(message);
        this.save(mongoMessage);
    }
}
