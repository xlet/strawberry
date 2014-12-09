package org.xlet.strawberry.persistent.mongo.domain.message.server;

import org.xlet.strawberry.core.message.server.ConnectedResponseMessage;
import org.xlet.strawberry.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午9:38.
 * Summary: ConnectedResponseMessage mongo defined.
 */
@Entity("connectedResponseMessage")
public class MongoConnectedResponseMessage extends ConnectedResponseMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;

    private long persistentTime;

    /**
     * constructor.
     */
    public MongoConnectedResponseMessage() {
        this.persistentTime = System.currentTimeMillis();
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
