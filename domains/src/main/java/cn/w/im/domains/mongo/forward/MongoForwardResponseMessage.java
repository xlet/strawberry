package cn.w.im.domains.mongo.forward;

import cn.w.im.domains.messages.forward.ForwardResponseMessage;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午9:25.
 * Summary: ForwardResponseMessage mongo defined.
 */
@Entity("forwardResponseMessage")
public class MongoForwardResponseMessage extends ForwardResponseMessage implements MongoDomain {

    @Id
    private ObjectId id;

    private long persistentDate;

    /**
     * constructor.
     */
    public MongoForwardResponseMessage() {
        this.persistentDate = new Date().getTime();
    }

    /**
     * constructor.
     * @param message ForwardResponseMessage.
     */
    public MongoForwardResponseMessage(ForwardResponseMessage message) {
        this();
        this.setMessageType(message.getMessageType());
        this.setSendTime(message.getSendTime());
        this.setReceivedTime(message.getReceivedTime());
        this.setErrorMessage(message.getErrorMessage());
        this.setErrorCode(message.getErrorCode());
        this.setFromServer(message.getFromServer());
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
