package cn.w.im.persistent.mongo.domain.message.client;

import cn.w.im.core.message.client.ConnectMessage;
import cn.w.im.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Creator: JackieHan.
 * longTime: 14-1-16 下午2:21.
 * Summary: 连接消息服务消息 mongo结构定义.
 */
@Entity("connectMessage")
public class MongoConnectMessage extends ConnectMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;

    private long persistentTime;


    /**
     * 构造函数.
     */
    public MongoConnectMessage() {
        this.persistentTime = System.currentTimeMillis();
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
