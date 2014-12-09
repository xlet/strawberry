package org.xlet.strawberry.persistent.mongo.domain.message.server;

import org.xlet.strawberry.core.message.server.ServerRegisterResponseMessage;
import org.xlet.strawberry.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-20 下午3:00.
 * Summary: ServerRegisterResponseMessage mongo define.
 */
@Entity("serverRegisterResponseMessage")
public class MongoServerRegisterResponseMessage extends ServerRegisterResponseMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;

    private long persistentTime;

    /**
     * default constructor.
     */
    public MongoServerRegisterResponseMessage() {
        this.persistentTime = System.currentTimeMillis();
    }

    /**
     * constructor.
     *
     * @param serverRegisterResponseMessage ServerRegisterResponseMessage.
     */
    public MongoServerRegisterResponseMessage(ServerRegisterResponseMessage serverRegisterResponseMessage) {
        this();
        this.setReceivedTime(serverRegisterResponseMessage.getReceivedTime());
        this.setSendTime(serverRegisterResponseMessage.getSendTime());
        this.setMessageType(serverRegisterResponseMessage.getMessageType());
        this.setStartedServers(serverRegisterResponseMessage.getStartedServers());
        this.setSuccess(serverRegisterResponseMessage.isSuccess());
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
        return persistentTime;
    }

    @Override
    public void setPersistentTime(long persistentTime) {
        this.persistentTime = persistentTime;
    }
}
