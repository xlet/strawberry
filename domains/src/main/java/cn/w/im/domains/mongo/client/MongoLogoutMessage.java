package cn.w.im.domains.mongo.client;

import cn.w.im.domains.messages.client.LogoutMessage;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-6 上午10:26.
 * Summary: Mongo 退出消息.
 */
@Entity("logoutMessages")
public class MongoLogoutMessage extends LogoutMessage implements MongoDomain {

    @Id
    private ObjectId id;

    private long persistentDate;

    /**
     * 构造函数  设置序列化时间为当前时间.
     */
    public MongoLogoutMessage() {
        this.persistentDate = new Date().getTime();
    }

    /**
     * 构造函数.
     * @param logoutMessage logoutMessage实例.
     */
    public MongoLogoutMessage(LogoutMessage logoutMessage) {
        this();
        this.setLoginId(logoutMessage.getLoginId());
        this.setReceivedTime(logoutMessage.getReceivedTime());
        this.setMessageType(logoutMessage.getMessageType());
        this.setSendTime(logoutMessage.getSendTime());
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
