package cn.w.im.domains.mongo.server;

import cn.w.im.domains.messages.server.ReadyMessage;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-19 下午2:01.
 * Summary: ready message mongo db defined.
 */
@Entity("readyMessage")
public class MongoReadyMessage extends ReadyMessage implements MongoDomain {

    @Id
    private ObjectId id;
    private long persistentDate;

    /**
     * default constructor.
     */
    public MongoReadyMessage() {
        persistentDate = new Date().getTime();
    }

    /**
     * constructor.
     *
     * @param message ready message.
     */
    public MongoReadyMessage(ReadyMessage message) {
        this.setReceivedTime(message.getReceivedTime());
        this.setMessageType(message.getMessageType());
        this.setMessageServer(message.getMessageServer());
        this.setSendTime(message.getSendTime());
    }

    @Override
    public ObjectId getId() {
        return this.id;
    }

    @Override
    public void setId(ObjectId id) {
        this.id = id;
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
