package cn.w.im.persistent.mongo.domain.message.server;

import cn.w.im.core.message.server.ForwardMessage;
import cn.w.im.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午2:45.
 * Summary: 转发消息 mongo 结构定义.
 */
@Entity("forwardMessage")
public class MongoForwardMessage extends ForwardMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;

    private long persistentTime;

    /**
     * 构造函数.
     */
    public MongoForwardMessage() {
        persistentTime = System.currentTimeMillis();
    }

    /**
     * 构造函数.
     *
     * @param forwardMessage 转发消息.
     */
    public MongoForwardMessage(ForwardMessage forwardMessage) {
        this();
        this.setSendTime(forwardMessage.getSendTime());
        this.setMessageType(forwardMessage.getMessageType());
        this.setMessage(forwardMessage.getMessage());
        this.setReceivedTime(forwardMessage.getReceivedTime());
        this.setFromServer(forwardMessage.getFromServer());
        this.setToServer(forwardMessage.getToServer());
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
