package cn.w.im.domains.mongo.basic;

import cn.w.im.domains.basic.OnlineMemberStatus;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * {@link cn.w.im.domains.basic.OnlineMemberStatus}'s mongo persistent defined.
 */
@Entity("onlineMemberStatus")
public class MongoOnlineMemberStatus extends OnlineMemberStatus implements MongoDomain {

    @Id
    private ObjectId id;
    private long persistentDate;

    /**
     * default constructor.
     */
    public MongoOnlineMemberStatus() {
        persistentDate = System.currentTimeMillis();
    }

    /**
     * constructor.
     *
     * @param onlineMemberStatus online member status.
     */
    public MongoOnlineMemberStatus(OnlineMemberStatus onlineMemberStatus) {
        this();
        this.setLoginId(onlineMemberStatus.getLoginId());
        this.setStatus(onlineMemberStatus.getStatus());
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
