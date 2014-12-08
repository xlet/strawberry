package cn.w.im.persistent.mongo.dao.message.server;

import cn.w.im.core.message.server.MemberLogoutMessage;
import cn.w.im.core.message.persistent.MessagePersistentProvider;
import cn.w.im.persistent.mongo.domain.message.server.MongoMemberLogoutMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component(value = "mongoMemberLogoutMessagePersistentProvider")
public class MongoMemberLogoutMessageDao extends BasicDAO<MongoMemberLogoutMessage, ObjectId>
        implements MessagePersistentProvider<MemberLogoutMessage> {

    @Autowired
    protected MongoMemberLogoutMessageDao(@Qualifier("dataStore") Datastore ds) {
        super(ds);
    }

    @Override
    public void save(MemberLogoutMessage message) {
        MongoMemberLogoutMessage mongoMessage = new MongoMemberLogoutMessage(message);
        this.save(mongoMessage);
    }
}
