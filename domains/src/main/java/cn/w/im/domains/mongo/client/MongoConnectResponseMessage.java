package cn.w.im.domains.mongo.client;

import cn.w.im.domains.messages.client.ConnectResponseMessage;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午8:43.
 * Summary:ConnectedResponseMessage mongo defined.
 */
@Entity("connectResponseMessage")
public class MongoConnectResponseMessage extends ConnectResponseMessage implements MongoDomain {

    @Id
    private ObjectId id;

    private long persistentDate;

    /**
     * constructor.
     */
    public MongoConnectResponseMessage() {
        this.persistentDate = new Date().getTime();
    }

    /**
     * constructor.
     *
     * @param message ConnectResponseMessage.
     */
    public MongoConnectResponseMessage(ConnectResponseMessage message) {
        this();
        this.setSuccess(message.isSuccess());
        this.setMessageType(message.getMessageType());
        this.setErrorCode(message.getErrorCode());
        this.setErrorMessage(message.getErrorMessage());
        this.setReceivedTime(message.getReceivedTime());
        this.setSendTime(message.getSendTime());
    }

    @Override
    public ObjectId getPersistentId() {
        return this.id;
    }

    @Override
    public void setPersistentId(ObjectId id) {
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
