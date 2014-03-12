package cn.w.im.domains.mongo;

import cn.w.im.domains.messages.TokenMessage;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Creator: JackieHan.
 * DateTime: 14-1-16 下午4:31.
 * Summary: TokenMessage mongo结构.
 */
@Entity("tokenMessage")
public class MongoTokenMessage extends TokenMessage implements MongoDomain {

    @Id
    private ObjectId id;

    private long persistentDate;

    /**
     * 构造函数.
     */
    public MongoTokenMessage() {
        persistentDate = new Date().getTime();
    }

    /**
     * 构造函数.
     *
     * @param tokenMessage tokenMessage
     */
    public MongoTokenMessage(TokenMessage tokenMessage) {
        this.setReceivedTime(tokenMessage.getReceivedTime());
        this.setMessageType(tokenMessage.getMessageType());
        this.setSendTime(tokenMessage.getSendTime());
        this.setToken(tokenMessage.getToken());
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
        return this.persistentDate;
    }

    @Override
    public void setPersistentDate(long persistentDate) {
        this.persistentDate = persistentDate;
    }
}
