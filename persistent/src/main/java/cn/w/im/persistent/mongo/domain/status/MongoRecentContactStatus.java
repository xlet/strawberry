package cn.w.im.persistent.mongo.domain.status;

import cn.w.im.persistent.mongo.domain.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

/**
 * mongo recent contact status defined.
 */
public class MongoRecentContactStatus implements MongoDomain {

    @Id
    private ObjectId persistentId;
    private long persistentTime;

    private String memberId1;
    private String memberId2;
    private String lastMessageContent;
    private long lastContactTime;

    public MongoRecentContactStatus() {
        this.persistentTime = System.currentTimeMillis();
    }

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

    public String getLastMessageContent() {
        return lastMessageContent;
    }

    public void setLastMessageContent(String lastMessageContent) {
        this.lastMessageContent = lastMessageContent;
    }

    public long getLastContactTime() {
        return lastContactTime;
    }

    public void setLastContactTime(long lastContactTime) {
        this.lastContactTime = lastContactTime;
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
    public long getPersistentTime() {
        return this.persistentTime;
    }

    @Override
    public void setPersistentTime(long persistentTime) {
        this.persistentTime = persistentTime;
    }
}
