package cn.w.im.domains.mongo.relation;

import cn.w.im.domains.member.TempMember;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * temp member of mongo.
 */
@Entity("tempMember")
public class MongoTempMember extends TempMember implements MongoDomain{

    @Id
    private ObjectId persistentId;
    private long persistentDate;

    public MongoTempMember() {
        this.persistentDate = System.currentTimeMillis();
    }

    public MongoTempMember(TempMember member) {
        this();
        this.setMemberSource(member.getMemberSource());
        this.setPicUrl(member.getPicUrl());
        this.setNickname(member.getNickname());
        this.setTempMember(member.isTempMember());
        this.setAddress(member.getAddress());
        this.setEmail(member.getEmail());
        this.setId(member.getId());
        this.setMobile(member.getMobile());
        this.setSignature(member.getSignature());
        this.setTelephone(member.getTelephone());
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
