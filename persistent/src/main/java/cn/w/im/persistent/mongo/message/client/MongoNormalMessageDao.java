package cn.w.im.persistent.mongo.message.client;

import cn.w.im.domains.messages.client.NormalMessage;
import cn.w.im.domains.mongo.client.MongoNormalMessage;
import cn.w.im.persistent.MessageDao;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 下午2:23.
 * Summary: MongoNormalMessageDao.
 */
@Component(value = "mongoNormalMessageDao")
public class MongoNormalMessageDao extends BasicDAO<MongoNormalMessage, ObjectId> implements MessageDao<NormalMessage> {

    /**
     * 构造函数.
     *
     * @param ds Datastore.
     */
    @Autowired
    protected MongoNormalMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(NormalMessage message) {
        MongoNormalMessage mongoMessage = new MongoNormalMessage(message);
        this.save(mongoMessage);
    }

    public List<NormalMessage> getNotReceivedMessage(String to) {
        List<NormalMessage> normalMessages = new ArrayList<NormalMessage>();
        Query query = this.getDatastore().createQuery(MongoNormalMessage.class).filter("to =", to).filter("forward =", false);
        QueryResults<MongoNormalMessage> messageQueryResults = this.find(query);
        for (MongoNormalMessage mongoNormalMessage : messageQueryResults.asList()) {
            mongoNormalMessage.setForward(true);
            NormalMessage normalMessage = new NormalMessage(mongoNormalMessage.getClientType(), mongoNormalMessage.getFrom(),
                    mongoNormalMessage.getTo(), mongoNormalMessage.getContent());
            normalMessages.add(normalMessage);
        }
        return normalMessages;
    }

    public List<NormalMessage> getNotReceivedMessage(String from, String to) {
        List<NormalMessage> normalMessages = new ArrayList<NormalMessage>();
        Query query = this.getDatastore().createQuery(MongoNormalMessage.class).filter("to =", to).filter("forward =", false).filter("from =", from);
        QueryResults<MongoNormalMessage> messageQueryResults = this.find(query);
        for (MongoNormalMessage mongoNormalMessage : messageQueryResults.asList()) {
            mongoNormalMessage.setForward(true);
            NormalMessage normalMessage = new NormalMessage(mongoNormalMessage.getClientType(), mongoNormalMessage.getFrom(),
                    mongoNormalMessage.getTo(), mongoNormalMessage.getContent());
            normalMessages.add(normalMessage);
        }
        UpdateOperations<MongoNormalMessage> updateForward = this.getDatastore().createUpdateOperations(MongoNormalMessage.class).set("forward", true);
        this.update(query, updateForward);
        return normalMessages;
    }

}
