package cn.w.im.domains.mongo.client;

import cn.w.im.domains.messages.client.GetProfileRequestMessage;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * mongo domain of {@link cn.w.im.domains.messages.client.GetProfileRequestMessage}.
 */
@Entity("getProfileRequestMessage")
public class MongoGetProfileRequestMessage extends GetProfileRequestMessage implements MongoDomain {

    @Id
    private ObjectId persistId;
    private long persistentDate;

    public MongoGetProfileRequestMessage() {
    }

    public MongoGetProfileRequestMessage(GetProfileRequestMessage message) {
        this.setSendTime(message.getSendTime());
        this.setMessageType(message.getMessageType());
        this.setIds(message.getIds());
        this.setReceivedTime(message.getReceivedTime());
    }

    @Override
    public ObjectId getPersistentId() {
        return this.persistId;
    }

    @Override
    public void setPersistentId(ObjectId id) {
        this.persistId = id;
    }

    @Override
    public long getPersistentDate() {
        return this.persistentDate;
    }

    @Override
    public void setPersistentDate(long persistentDate) {
        this.persistentDate = persistentDate;
    }
}
