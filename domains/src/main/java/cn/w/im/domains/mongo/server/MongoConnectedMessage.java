package cn.w.im.domains.mongo.server;

import cn.w.im.domains.messages.server.ConnectedMessage;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-27 下午9:33.
 * Summary: ConnectedMessage mongo defined.
 */
@Entity("connectedMessage")
public class MongoConnectedMessage extends ConnectedMessage implements MongoDomain {

    @Id
    private ObjectId id;

    private long persistentDate;

    /**
     * constructor.
     */
    public MongoConnectedMessage() {
        this.persistentDate = new Date().getTime();
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
        this.setConnectedClient(this.getConnectedClient());
        this.setRespondKey(this.getRespondKey());
        this.setToken(this.getToken());
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
        return this.persistentDate;
    }

    @Override
    public void setPersistentDate(long persistentDate) {
        this.persistentDate = persistentDate;
    }
}
