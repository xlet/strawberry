package cn.w.im.domains.mongo.server;

import cn.w.im.domains.messages.server.ConnectedResponseMessage;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午9:38.
 * Summary: ConnectedResponseMessage mongo defined.
 */
@Entity("connectedResponseMessage")
public class MongoConnectedResponseMessage extends ConnectedResponseMessage implements MongoDomain {

    @Id
    private ObjectId id;

    private long persistentDate;

    /**
     * constructor.
     */
    public MongoConnectedResponseMessage() {
        this.persistentDate = new Date().getTime();
    }

    /**
     * constructor.
     *
     * @param message ConnectedResponseMessage.
     */
    public MongoConnectedResponseMessage(ConnectedResponseMessage message) {
        this();
        this.setMessageType(message.getMessageType());
        this.setSendTime(message.getSendTime());
        this.setReceivedTime(message.getReceivedTime());
        this.setErrorMessage(this.getErrorMessage());
        this.setRespondKey(this.getRespondKey());
        this.setToken(this.getToken());
        this.setFromServer(this.getFromServer());
        this.setErrorCode(this.getErrorCode());
        this.setSuccess(this.isSuccess());
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
