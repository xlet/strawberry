package cn.w.im.persistent.mongo.message.server;

import cn.w.im.domains.messages.server.ResponseLinkedClientsMessage;
import cn.w.im.domains.mongo.server.MongoResponseLinkedClientsMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-18 下午2:47.
 * Summary:
 */
public class MongoResponseLinkedClientsMessageDao extends BasicDAO<MongoResponseLinkedClientsMessage, ObjectId> implements MessageDao<ResponseLinkedClientsMessage> {

    /**
     * constructor.
     *
     * @param ds datastore.
     */
    public MongoResponseLinkedClientsMessageDao(Datastore ds) {
        super(ds);
    }

    @Override
    public void save(ResponseLinkedClientsMessage message) {
        MongoResponseLinkedClientsMessage mongoMessage = new MongoResponseLinkedClientsMessage(message);
        this.save(mongoMessage);
    }
}
