package cn.w.im.domains.mongo.server;

import cn.w.im.domains.messages.server.TokenResponseMessage;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午9:45.
 * Summary: TokenResponseMessage mongo defined.
 */
@Entity("TokenResponseMessage")
public class MongoTokenResponseMessage extends TokenResponseMessage implements MongoDomain {

    @Id
    private ObjectId id;

    private long persistentDate;

    /**
     * constructor.
     */
    public MongoTokenResponseMessage() {
        this.persistentDate = new Date().getTime();
    }

    /**
     * constructor.
     *
     * @param message TokenResponseMessage.
     */
    public MongoTokenResponseMessage(TokenResponseMessage message) {
        this();
        this.setMessageType(message.getMessageType());
        this.setSendTime(message.getSendTime());
        this.setReceivedTime(message.getReceivedTime());
        this.setFromServer(message.getFromServer());
        this.setSuccess(message.isSuccess());
        this.setErrorCode(message.getErrorCode());
        this.setErrorMessage(message.getErrorMessage());
        this.setRespondKey(message.getRespondKey());
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
        return this.persistentDate;
    }

    @Override
    public void setPersistentDate(long persistentDate) {
        this.persistentDate = persistentDate;
    }
}
