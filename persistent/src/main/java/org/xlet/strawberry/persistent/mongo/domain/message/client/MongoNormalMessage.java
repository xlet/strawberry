package org.xlet.strawberry.persistent.mongo.domain.message.client;

import org.xlet.strawberry.core.message.client.NormalMessage;
import org.xlet.strawberry.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 下午2:14.
 * Summary: mongo 一般消息.
 */
@Entity("messages")
public class MongoNormalMessage extends NormalMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;

    private long persistentTime;

    /**
     * 构造函数  设置序列化时间为当前时间.
     */
    public MongoNormalMessage() {
        this.persistentTime = System.currentTimeMillis();
    }

    /**
     * 构造函数.
     *
     * @param normalMessage normalMessage实例.
     */
    public MongoNormalMessage(NormalMessage normalMessage) {
        this();
        this.setReceivedTime(normalMessage.getReceivedTime());
        this.setMessageType(normalMessage.getMessageType());
        this.setSendTime(normalMessage.getSendTime());
        this.setContent(normalMessage.getContent());
        this.setFrom(normalMessage.getFrom());
        this.setTo(normalMessage.getTo());
        this.setForward(normalMessage.isForward());
    }

    @Override
    public long getPersistentTime() {
        return persistentTime;
    }

    @Override
    public void setPersistentTime(long persistentTime) {
        this.persistentTime = persistentTime;
    }

    @Override
    public ObjectId getPersistentId() {
        return persistentId;
    }

    @Override
    public void setPersistentId(ObjectId id) {
        this.persistentId = id;
    }
}
