package cn.w.im.domains.mongo.server;

import cn.w.im.domains.messages.server.RequestLinkedClientsMessage;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-2-11 下午2:45.
 * Summary: 请求已连接客户端消息 mongo结构定义.
 */
@Entity("requestLinkedClientMessage")
public class MongoRequestLinkedClientsMessage extends RequestLinkedClientsMessage implements MongoDomain {

    @Id
    private ObjectId id;

    private long persistentDate;

    /**
     * 构造函数.
     */
    public MongoRequestLinkedClientsMessage() {
        persistentDate = new Date().getTime();
    }

    /**
     * 构造函数.
     *
     * @param requestLinkedClientsMessage 请求已连接客户端消息.
     */
    public MongoRequestLinkedClientsMessage(RequestLinkedClientsMessage requestLinkedClientsMessage) {
        this();
        this.setSendTime(requestLinkedClientsMessage.getSendTime());
        this.setMessageType(requestLinkedClientsMessage.getMessageType());
        this.setReceivedTime(requestLinkedClientsMessage.getReceivedTime());
        this.setRequestServer(requestLinkedClientsMessage.getRequestServer());
    }

    @Override
    public ObjectId getPersistentId() {
        return this.id;
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
