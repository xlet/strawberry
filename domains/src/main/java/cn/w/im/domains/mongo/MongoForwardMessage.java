package cn.w.im.domains.mongo;

import cn.w.im.domains.messages.ForwardMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午2:45.
 * Summary: 转发消息 mongo 结构定义.
 */
@Entity("forwardMessage")
public class MongoForwardMessage extends ForwardMessage implements MongoDomain {

    @Id
    private ObjectId id;

    private long persistentDate;

    /**
     * 构造函数.
     */
    public MongoForwardMessage() {
        persistentDate = new Date().getTime();
    }

    /**
     * 构造函数.
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
