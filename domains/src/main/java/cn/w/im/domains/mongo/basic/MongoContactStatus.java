package cn.w.im.domains.mongo.basic;

import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * mongo contact status.
 */
@Entity("contractStatus")
public class MongoContactStatus implements MongoDomain {

    @Id
    private ObjectId persistentId;
    private long persistentDate;

    private String memberId1, memberId2;
    private long lastContactTime;
    private String lastMessage;

    public String getMemberId1() {
        return memberId1;
    }

    public void setMemberId1(String memberId1) {
        this.memberId1 = memberId1;
    }

    public String getMemberId2() {
        return memberId2;
    }

    public void setMemberId2(String memberId2) {
        this.memberId2 = memberId2;
    }

    public long getLastContactTime() {
        return lastContactTime;
    }

    public void setLastContactTime(long lastContactTime) {
        this.lastContactTime = lastContactTime;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public ObjectId getPersistentId() {
        return this.persistentId;
    }

    @Override
    public void setPersistentId(ObjectId persistentId) {
        this.persistentId = persistentId;
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
