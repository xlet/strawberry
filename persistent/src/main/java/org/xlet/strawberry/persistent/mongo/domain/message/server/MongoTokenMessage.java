package org.xlet.strawberry.persistent.mongo.domain.message.server;

import org.xlet.strawberry.core.message.server.TokenMessage;
import org.xlet.strawberry.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午4:31.
 * Summary: TokenMessage mongo结构.
 */
@Entity("tokenMessage")
public class MongoTokenMessage extends TokenMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;

    private long persistentTime;

    /**
     * 构造函数.
     */
    public MongoTokenMessage() {
        persistentTime = System.currentTimeMillis();
    }

    /**
     * 构造函数.
     *
     * @param tokenMessage tokenMessage
     */
    public MongoTokenMessage(TokenMessage tokenMessage) {
        this.setReceivedTime(tokenMessage.getReceivedTime());
        this.setMessageType(tokenMessage.getMessageType());
        this.setSendTime(tokenMessage.getSendTime());
        this.setToken(tokenMessage.getToken());
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
