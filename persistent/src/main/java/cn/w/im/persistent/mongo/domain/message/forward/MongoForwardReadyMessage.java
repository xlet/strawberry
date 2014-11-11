package cn.w.im.persistent.mongo.domain.message.forward;

import cn.w.im.core.message.forward.ForwardReadyMessage;
import cn.w.im.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午9:16.
 * Summary: ForwardReadyMessage mongo defined.
 */
@Entity("forwardReadyMessage")
public class MongoForwardReadyMessage extends ForwardReadyMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;

    private long persistentTime;

    /**
     * constructor.
     */
    public MongoForwardReadyMessage() {
        this.persistentTime = System.currentTimeMillis();
    }

    /**
     * constructor.
     *
     * @param message ForwardReadyMessage.
     */
    public MongoForwardReadyMessage(ForwardReadyMessage message) {
        this();
        this.setMessageType(message.getMessageType());
        this.setSendTime(message.getSendTime());
        this.setReceivedTime(message.getReceivedTime());
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
