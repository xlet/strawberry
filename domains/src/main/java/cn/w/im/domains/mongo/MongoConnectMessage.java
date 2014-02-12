package cn.w.im.domains.mongo;

import cn.w.im.domains.messages.ConnectMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午2:21.
 * Summary: 连接消息服务消息 mongo结构定义
 */
@Entity("connectMessage")
public class MongoConnectMessage extends ConnectMessage implements MongoDomain {

    @Id
    private ObjectId id;

    private Date persistentDate;


    /**
     * 构造函数.
     */
    public MongoConnectMessage() {
        this.persistentDate = new Date();
    }

    /**
     * 构造函数.
     *
     * @param connectMessage 连接消息.
     */
    public MongoConnectMessage(ConnectMessage connectMessage) {
        this();
        this.setReceivedTime(connectMessage.getReceivedTime());
        this.setMessageType(connectMessage.getMessageType());
        this.setSendTime(connectMessage.getSendTime());
        this.setToken(connectMessage.getToken());
    }

    @Override
    public ObjectId getId() {
        return id;
    }

    @Override
    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public Date getPersistentDate() {
        return persistentDate;
    }

    @Override
    public void setPersistentDate(Date persistentDate) {
        this.persistentDate = persistentDate;
    }
}
