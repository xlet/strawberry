package cn.w.im.persistent.mongo.domain.message.server;

import cn.w.im.core.message.server.ConnectedMessage;
import cn.w.im.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午9:33.
 * Summary: ConnectedMessage mongo defined.
 */
@Entity("connectedMessage")
public class MongoConnectedMessage extends ConnectedMessage implements MongoDomain {

    @Id
    private ObjectId persistentId;

    private long persistentTime;

    /**
     * constructor.
     */
    public MongoConnectedMessage() {
        this.persistentTime = System.currentTimeMillis();
    }

    /**
     * constructor.
     *
     * @param message ConnectedMessage.
     */
    public MongoConnectedMessage(ConnectedMessage message) {
        this();
        this.setMessageType(message.getMessageType());
        this.setSendTime(message.getSendTime());
        this.setReceivedTime(message.getReceivedTime());
        this.setFromServer(this.getFromServer());
        this.setMemberId(message.getMemberId());
        this.setClientHost(message.getClientHost());
        this.setClientType(message.getClientType());
        this.setRespondKey(this.getRespondKey());
        this.setToken(this.getToken());
    }

    @Override
    public ObjectId getPersistentId() {
        return this.persistentId;
    }

    @Override
    public void setPersistentId(ObjectId id) {
        this.persistentId = id;
    }

    @Override
    public long getPersistentTime() {
        return this.persistentTime;
    }

    @Override
    public void setPersistentTime(long persistentTime) {
        this.persistentTime = persistentTime;
    }
}
