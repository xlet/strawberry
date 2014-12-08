package cn.w.im.persistent.mongo.domain.message.client;

import cn.w.im.core.message.client.LoginMessage;
import cn.w.im.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 13-12-31 下午2:07.
 * Summary: Mongo 登陆信息.
 */
@Entity("loginMessages")
public class MongoLoginMessage extends LoginMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;
    private long persistentTime;

    /**
     * 构造函数  设置序列化时间为当前时间.
     */
    public MongoLoginMessage() {
        this.persistentTime = new Date().getTime();
    }

    /**
     * 构造函数.
     *
     * @param loginMessage loginMessage实例.
     */
    public MongoLoginMessage(LoginMessage loginMessage) {
        this();
        this.setLoginId(loginMessage.getLoginId());
        this.setReceivedTime(loginMessage.getReceivedTime());
        this.setMessageType(loginMessage.getMessageType());
        this.setPassword(loginMessage.getPassword());
        this.setSendTime(loginMessage.getSendTime());
    }

    @Override
    public long getPersistentTime() {
        return persistentTime;
    }

    @Override
    public void setPersistentTime(long persistentDate) {
        this.persistentTime = persistentDate;
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
