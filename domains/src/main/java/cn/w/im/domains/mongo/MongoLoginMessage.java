package cn.w.im.domains.mongo;

import cn.w.im.domains.messages.LoginMessage;
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
public class MongoLoginMessage extends LoginMessage {

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
    public MongoLoginMessage() {
        this.serializationTime = new Date();
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
