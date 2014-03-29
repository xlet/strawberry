package cn.w.im.domains.mongo.client;

import cn.w.im.domains.messages.client.LoginResponseMessage;
import cn.w.im.domains.mongo.MongoDomain;
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
    private ObjectId id;

    private long persistenDate;

    /**
     * constructor.
     */
    public MongoLoginResponseMessage() {
        this.persistenDate = new Date().getTime();
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
    public ObjectId getId() {
        return this.id;
    }

    @Override
    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public long getPersistentDate() {
        return this.persistenDate;
    }

    @Override
    public void setPersistentDate(long persistentDate) {
        this.persistenDate = persistentDate;
    }
}
