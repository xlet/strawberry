package cn.w.im.persistent.mongo.domain.message.server;

import cn.w.im.core.message.server.ReadyMessage;
import cn.w.im.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-19 下午2:01.
 * Summary: ready message mongo db defined.
 */
@Entity("readyMessage")
public class MongoReadyMessage extends ReadyMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;
    private long persistentTime;

    /**
     * default constructor.
     */
    public MongoReadyMessage() {
        persistentTime = System.currentTimeMillis();
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
    public ObjectId getPersistentId() {
        return this.persistentId;
    }

    @Override
    public void setPersistentId(ObjectId id) {
        this.persistentId = id;
    }

    @Override
    public long getPersistentTime() {
        return this.persistentTime;
    }

    @Override
    public void setPersistentTime(long persistentTime) {
        this.persistentTime = persistentTime;
    }
}
