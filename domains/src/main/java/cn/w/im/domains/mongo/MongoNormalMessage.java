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
public class MongoNormalMessage extends NormalMessage {

    /**
     * id.
     */
    @Id
    private ObjectId id;

    /**
     * 序列化名称.
     */
    private Date serializationTime;

    /**
     * 构造函数  设置序列化时间为当前时间.
     */
    public MongoNormalMessage() {
        this.serializationTime = new Date();
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

    /**
     * 获取序列化时间.
     * @return 序列化时间.
     */
    public Date getSerializationTime() {
        return serializationTime;
    }

    /**
     * 设置序列化时间.
     * @param serializationTime 序列化时间.
     */
    public void setSerializationTime(Date serializationTime) {
        this.serializationTime = serializationTime;
    }

    /**
     * 获取id.
     * @return id.
     */
    public ObjectId getId() {
        return id;
    }

    /**
     * 设置id.
     * @param id id.
     */
    public void setId(ObjectId id) {
        this.id = id;
    }
}
