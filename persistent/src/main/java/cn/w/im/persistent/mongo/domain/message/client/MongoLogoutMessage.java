package cn.w.im.persistent.mongo.domain.message.client;

import cn.w.im.core.message.client.LogoutMessage;
import cn.w.im.persistent.mongo.domain.MongoDomain;
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
    private ObjectId persistentId;

    private long persistentTime;

    /**
     * 构造函数  设置序列化时间为当前时间.
     */
    public MongoLogoutMessage() {
        this.persistentTime = new Date().getTime();
    }

    /**
     * 构造函数.
     *
     * @param logoutMessage logoutMessage实例.
     */
    public MongoLogoutMessage(LogoutMessage logoutMessage) {
        this();
        this.setMemberId(logoutMessage.getMemberId());
        this.setReceivedTime(logoutMessage.getReceivedTime());
        this.setMessageType(logoutMessage.getMessageType());
        this.setSendTime(logoutMessage.getSendTime());
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
