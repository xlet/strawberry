package cn.w.im.persistent.mongo.dao.message.client;

import cn.w.im.core.providers.persistent.NormalMessagePersistentProvider;
import cn.w.im.core.member.BasicMember;
import cn.w.im.core.message.client.NormalMessage;
import cn.w.im.persistent.mongo.domain.message.client.MongoNormalMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 下午2:23.
 * Summary: MongoNormalMessageDao.
 */
@Component(value = "mongoNormalMessagePersistentProvider")
public class MongoNormalMessageDao extends BasicDAO<MongoNormalMessage, ObjectId>
        implements NormalMessagePersistentProvider {

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

    @Override
    public Collection<NormalMessage> getOfflineMessages(BasicMember owner) {
        Collection<NormalMessage> normalMessages = new ArrayList<NormalMessage>();
        Query query = this.getDatastore().createQuery(MongoNormalMessage.class).filter("to =", owner.getId()).filter("forward =", false);
        QueryResults<MongoNormalMessage> messageQueryResults = this.find(query);
        for (MongoNormalMessage mongoNormalMessage : messageQueryResults.asList()) {
            mongoNormalMessage.setForward(true);
            NormalMessage normalMessage = new NormalMessage(mongoNormalMessage.getClientType(), mongoNormalMessage.getFrom(),
                    mongoNormalMessage.getTo(), mongoNormalMessage.getContent());
            normalMessages.add(normalMessage);
        }
        return normalMessages;
    }

    @Override
    public int setMessageForwarded(BasicMember owner) {
        Query query = this.getDatastore().createQuery(MongoNormalMessage.class).filter("to =", owner.getId()).filter("forward =", false);
        UpdateOperations<MongoNormalMessage> updateForward = this.getDatastore()
                .createUpdateOperations(MongoNormalMessage.class).set("forward", true);
        UpdateResults updateResults = this.update(query, updateForward);
        return updateResults.getUpdatedCount();
    }
}
