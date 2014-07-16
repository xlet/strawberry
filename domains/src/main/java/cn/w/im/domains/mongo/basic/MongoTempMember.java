package cn.w.im.domains.mongo.basic;

import cn.w.im.domains.basic.TempMember;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * @author jackie.
 *         temp member.
 */
@Entity("tempMember")
public class MongoTempMember extends TempMember implements MongoDomain {

    public MongoTempMember() {
        this.persistentDate = System.currentTimeMillis();
    }

    public MongoTempMember(TempMember tempMember) {
        this();
        this.setName(tempMember.getName());
        this.setNickname(tempMember.getNickname());
        this.setSource(tempMember.getSource());
    }

    @Id
    private ObjectId id;
    private long persistentDate;

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
