package cn.w.im.domains.mongo.client;

import cn.w.im.domains.messages.client.LoginMessage;
import cn.w.im.domains.mongo.MongoDomain;
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
    private ObjectId id;
    private long persistentDate;

    /**
     * 构造函数  设置序列化时间为当前时间.
     */
    public MongoLoginMessage() {
        this.persistentDate = new Date().getTime();
    }

    /**
     * 构造函数.
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
    public long getPersistentDate() {
        return persistentDate;
    }

    @Override
    public void setPersistentDate(long persistentDate) {
        this.persistentDate = persistentDate;
    }

    @Override
    public ObjectId getPersistentId() {
        return id;
    }

    @Override
    public void setPersistentId(ObjectId id) {
        this.id = id;
    }
}
