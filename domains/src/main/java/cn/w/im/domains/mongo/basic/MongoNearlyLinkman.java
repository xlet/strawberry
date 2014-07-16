package cn.w.im.domains.mongo.basic;

import cn.w.im.domains.basic.NearlyLinkman;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * mongo domain of {@link cn.w.im.domains.basic.NearlyLinkman}.
 */
@Entity("nearlyLinkman")
public class MongoNearlyLinkman extends NearlyLinkman implements MongoDomain {

    @Id
    private ObjectId id;
    private long persistentDate;

    public MongoNearlyLinkman(){

    }

    public MongoNearlyLinkman(String memberId1, String memberId2) {
        super(memberId1, memberId2);
        this.persistentDate = System.currentTimeMillis();
    }

    public MongoNearlyLinkman(NearlyLinkman nearlyLinkman) {
        this(nearlyLinkman.getMemberId1(), nearlyLinkman.getMemberId2());
        this.setLastLinkTime(nearlyLinkman.getLastLinkTime());
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
