package cn.w.im.domains.mongo.relation;

import cn.w.im.domains.member.WcnMember;
import cn.w.im.domains.mongo.MongoDomain;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * w.cn member of Mongo.
 */
@Entity("wcnMember")
public class MongoWcnMember extends WcnMember implements MongoDomain {

    @Id
    private ObjectId persistentId;
    private long persistentDate;

    public MongoWcnMember() {
        this.persistentDate = System.currentTimeMillis();
    }

    public MongoWcnMember(WcnMember member) {
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
        this.setMerchant(member.isMerchant());
        this.setContractor(member.getContractor());
        this.setShopName(member.getShopName());
        this.setMobileValid(member.isMobileValid());
        this.setRealNameValid(member.isRealNameValid());
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
