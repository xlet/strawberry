package cn.w.im.mongo.dao.message.server;

import cn.w.im.domains.messages.server.TokenResponseMessage;
import cn.w.im.domains.mongo.server.MongoTokenResponseMessage;
import cn.w.im.mongo.dao.message.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-29 下午6:12.
 * Summary: MongoTokenResponseMessage dao.
 */
public class MongoTokenResponseMessageDao extends BasicDAO<MongoTokenResponseMessage, ObjectId> implements MessageDao<TokenResponseMessage> {
    /**
     * constructor.
     *
     * @param ds data store.
     */
    public MongoTokenResponseMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(TokenResponseMessage message) {
        MongoTokenResponseMessage mongoMessage = new MongoTokenResponseMessage(message);
        this.save(mongoMessage);
    }
}
