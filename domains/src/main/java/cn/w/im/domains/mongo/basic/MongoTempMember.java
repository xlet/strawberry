package cn.w.im.domains.mongo.basic;

import cn.w.im.domains.member.TempMember;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * temp member.
 */
@Entity("tempMember")
public class MongoTempMember extends TempMember implements MongoDomain {

    public MongoTempMember() {
        this.persistentDate = System.currentTimeMillis();
    }

    public MongoTempMember(TempMember tempMember) {
        this();
        this.setId(tempMember.getId());
        this.setNickname(tempMember.getNickname());
        this.setSource(tempMember.getSource());
    }

    @Id
    private ObjectId persistId;
    private long persistentDate;

    @Override
    public ObjectId getPersistentId() {
        return this.persistId;
    }

    @Override
    public void setPersistentId(ObjectId id) {
        this.persistId = id;
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
