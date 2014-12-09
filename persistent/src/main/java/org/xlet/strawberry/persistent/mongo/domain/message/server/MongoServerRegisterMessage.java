package org.xlet.strawberry.persistent.mongo.domain.message.server;

import org.xlet.strawberry.core.message.server.ServerRegisterMessage;
import org.xlet.strawberry.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 下午4:55.
 * Summary: 消息服务注册消息Mongo结构定义.
 */
@Entity("serverRegisterMessage")
public class MongoServerRegisterMessage extends ServerRegisterMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;

    private long persistentTime;

    /**
     * 构造函数.
     */
    public MongoServerRegisterMessage() {
        this.persistentTime = System.currentTimeMillis();
    }

    /**
     * 构造函数.
     *
     * @param messageServerRegisterMessage 注册消息类型.
     */
    public MongoServerRegisterMessage(ServerRegisterMessage messageServerRegisterMessage) {
        this();
        this.setSendTime(messageServerRegisterMessage.getSendTime());
        this.setMessageType(messageServerRegisterMessage.getMessageType());
        this.setReceivedTime(messageServerRegisterMessage.getReceivedTime());
        this.setServerBasic(messageServerRegisterMessage.getServerBasic());
    }

    @Override
    public ObjectId getPersistentId() {
        return persistentId;
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
