package cn.w.im.domains.mongo.client;

import cn.w.im.domains.messages.client.LogoutResponseMessage;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午9:07.
 * Summary:LogoutResponseMessage mongo defined.
 */
@Entity("logoutResponseMessage")
public class MongoLogoutResponseMessage extends LogoutResponseMessage implements MongoDomain {

    @Id
    private ObjectId id;

    private long persistentDate;


    /**
     * constructor.
     */
    public MongoLogoutResponseMessage() {
        this.persistentDate = new Date().getTime();
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
