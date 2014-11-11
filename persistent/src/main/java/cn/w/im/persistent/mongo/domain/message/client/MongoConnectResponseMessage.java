package cn.w.im.persistent.mongo.domain.message.client;

import cn.w.im.core.message.client.ConnectResponseMessage;
import cn.w.im.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午8:43.
 * Summary:ConnectedResponseMessage mongo defined.
 */
@Entity("connectResponseMessage")
public class MongoConnectResponseMessage extends ConnectResponseMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;

    private long persistentTime;

    /**
     * constructor.
     */
    public MongoConnectResponseMessage() {
        this.persistentTime = System.currentTimeMillis();
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
