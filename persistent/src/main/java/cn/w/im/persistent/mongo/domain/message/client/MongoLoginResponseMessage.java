package cn.w.im.persistent.mongo.domain.message.client;

import cn.w.im.core.message.client.LoginResponseMessage;
import cn.w.im.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午8:50.
 * Summary:LoginResponseMessage mongo define.
 */
@Entity("loginResponseMessage")
public class MongoLoginResponseMessage extends LoginResponseMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;

    private long persistentTime;

    /**
     * constructor.
     */
    public MongoLoginResponseMessage() {
        this.persistentTime = new Date().getTime();
    }

    /**
     * constructor.
     *
     * @param message LoginResponseMessage.
     */
    public MongoLoginResponseMessage(LoginResponseMessage message) {
        this();
        this.setErrorMessage(message.getErrorMessage());
        this.setReceivedTime(message.getReceivedTime());
        this.setErrorCode(message.getErrorCode());
        this.setSendTime(message.getSendTime());
        this.setSuccess(message.isSuccess());
        this.setLoggedOtherPlace(message.getLoggedOtherPlace());
        this.setMessageType(message.getMessageType());
        this.setToken(message.getToken());
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
    public void setPersistentTime(long persistentDate) {
        this.persistentTime = persistentDate;
    }
}
