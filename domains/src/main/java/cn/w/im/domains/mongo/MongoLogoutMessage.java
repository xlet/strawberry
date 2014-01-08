package cn.w.im.domains.mongo;

import cn.w.im.domains.messages.LogoutMessage;
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
public class MongoLogoutMessage extends LogoutMessage {

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
    public MongoLogoutMessage() {
        this.serializationTime = new Date();
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
