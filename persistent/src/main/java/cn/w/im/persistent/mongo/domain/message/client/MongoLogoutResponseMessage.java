package cn.w.im.persistent.mongo.domain.message.client;

import cn.w.im.core.message.client.LogoutResponseMessage;
import cn.w.im.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午9:07.
 * Summary:LogoutResponseMessage mongo defined.
 */
@Entity("logoutResponseMessage")
public class MongoLogoutResponseMessage extends LogoutResponseMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;

    private long persistentTime;


    /**
     * constructor.
     */
    public MongoLogoutResponseMessage() {
        this.persistentTime = System.currentTimeMillis();
    }

    /**
     * constructor.
     *
     * @param message LogoutResponseMessage.
     */
    public MongoLogoutResponseMessage(LogoutResponseMessage message) {
        this();
        this.setMessageType(message.getMessageType());
        this.setSuccess(message.isSuccess());
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
