package cn.w.im.persistent.mongo.domain.message.server;

import cn.w.im.core.message.server.TokenResponseMessage;
import cn.w.im.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午9:45.
 * Summary: TokenResponseMessage mongo defined.
 */
@Entity("TokenResponseMessage")
public class MongoTokenResponseMessage extends TokenResponseMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;

    private long persistentTime;

    /**
     * constructor.
     */
    public MongoTokenResponseMessage() {
        this.persistentTime = System.currentTimeMillis();
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
