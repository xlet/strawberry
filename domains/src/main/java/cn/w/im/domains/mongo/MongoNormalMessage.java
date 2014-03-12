package cn.w.im.domains.mongo;

import cn.w.im.domains.messages.NormalMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 下午2:14.
 * Summary: mongo 一般消息.
 */
@Entity("messages")
public class MongoNormalMessage extends NormalMessage implements MongoDomain {

    @Id
    private ObjectId id;

    private long persistentDate;

    /**
     * 构造函数  设置序列化时间为当前时间.
     */
    public MongoNormalMessage() {
        this.persistentDate = new Date().getTime();
    }

    /**
     * 构造函数.
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
    }

    @Override
    public long getPersistentDate() {
        return persistentDate;
    }

    @Override
    public void setPersistentDate(long persistentDate) {
        this.persistentDate = persistentDate;
    }

    @Override
    public ObjectId getId() {
        return id;
    }

    @Override
    public void setId(ObjectId id) {
        this.id = id;
    }
}
