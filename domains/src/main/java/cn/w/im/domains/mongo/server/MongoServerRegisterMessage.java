package cn.w.im.domains.mongo.server;

import cn.w.im.domains.messages.server.ServerRegisterMessage;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-9 下午4:55.
 * Summary: 消息服务注册消息Mongo结构定义.
 */
@Entity("serverRegisterMessage")
public class MongoServerRegisterMessage extends ServerRegisterMessage implements MongoDomain {

    @Id
    private ObjectId id;

    private long persistentDate;

    /**
     * 构造函数.
     */
    public MongoServerRegisterMessage() {

        this.persistentDate = new Date().getTime();
    }

    /**
     * 构造函数.
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
        return id;
    }

    @Override
    public void setPersistentId(ObjectId id) {
        this.id = id;
    }

    @Override
    public long getPersistentDate() {
        return persistentDate;
    }

    @Override
    public void setPersistentDate(long persistentDate) {
        this.persistentDate = persistentDate;
    }
}
