package cn.w.im.domains.mongo.server;

import cn.w.im.domains.messages.server.ResponseLinkedClientsMessage;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-18 下午2:37.
 * Summary: mongo ResponseLinkedLClientsMessage
 */
@Entity("responseLinkedClientsMessage")
public class MongoResponseLinkedClientsMessage extends ResponseLinkedClientsMessage implements MongoDomain {

    @Id
    private ObjectId id;
    private long persistentDate;

    /**
     * 构造函数.
     */
    public MongoResponseLinkedClientsMessage() {
        persistentDate = new Date().getTime();
    }

    /**
     * 构造函数.
     *
     * @param message ResponseLinkedClientsMessage
     */
    public MongoResponseLinkedClientsMessage(ResponseLinkedClientsMessage message) {
        this();
        this.setLinkedClients(message.getLinkedClients());
        this.setMessageServer(message.getMessageServer());
        this.setMessageType(message.getMessageType());
        this.setReceivedTime(message.getReceivedTime());
        this.setSendTime(message.getSendTime());
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
    public long getPersistentDate() {
        return persistentDate;
    }

    @Override
    public void setPersistentDate(long persistentDate) {
        this.persistentDate = persistentDate;
    }
}
