package cn.w.im.domains.mongo.server;

import cn.w.im.domains.messages.server.ServerRegisterResponseMessage;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-3-20 下午3:00.
 * Summary: ServerRegisterResponseMessage mongo define.
 */
@Entity("serverRegisterResponseMessage")
public class MongoServerRegisterResponseMessage extends ServerRegisterResponseMessage implements MongoDomain {

    @Id
    private ObjectId id;

    private long persistentDate;

    /**
     * default constructor.
     */
    public MongoServerRegisterResponseMessage() {
        this.persistentDate = new Date().getTime();
    }

    /**
     * constructor.
     *
     * @param serverRegisterResponseMessage ServerRegisterResponseMessage.
     */
    public MongoServerRegisterResponseMessage(ServerRegisterResponseMessage serverRegisterResponseMessage) {
        this();
        this.setReceivedTime(serverRegisterResponseMessage.getReceivedTime());
        this.setSendTime(serverRegisterResponseMessage.getSendTime());
        this.setMessageType(serverRegisterResponseMessage.getMessageType());
        this.setStartedServers(serverRegisterResponseMessage.getStartedServers());
        this.setSuccess(serverRegisterResponseMessage.isSuccess());
    }

    @Override
    public ObjectId getId() {
        return this.id;
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
