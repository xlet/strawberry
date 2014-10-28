package cn.w.im.domains.mongo.relation;

import cn.w.im.domains.member.NotInitMember;
import cn.w.im.domains.mongo.MongoDomain;
import cn.w.im.domains.relation.FriendGroup;
import cn.w.im.domains.relation.MemberRelation;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Relation of Mongo.
 */
@Entity("memberRelation")
public class MongoMemberRelation implements MongoDomain {
    @Id
    private ObjectId persistentId;
    private long persistentDate;

    private String owner;
    private List<MongoFriendGroup> friendGroups;
    private long createTime;
    private long lastUpdateTime;
    private String version;

    @Override
    public ObjectId getPersistentId() {
        return this.persistentId;
    }

    @Override
    public void setPersistentId(ObjectId id) {
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<MongoFriendGroup> getFriendGroups() {
        return friendGroups;
    }

    public void setFriendGroups(List<MongoFriendGroup> friendGroups) {
        this.friendGroups = friendGroups;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public MemberRelation createMemberRelation() {
        MemberRelation memberRelation = new MemberRelation();
        memberRelation.setVersion(this.getVersion());
        memberRelation.setOwner(new NotInitMember(this.getOwner()));
        memberRelation.setLastUpdateTime(this.getLastUpdateTime());
        memberRelation.setCreateTime(this.getCreateTime());
        memberRelation.setVersion(this.getVersion());
        memberRelation.setGroups(new ArrayList<FriendGroup>());
        for (MongoFriendGroup mongoFriendGroup : this.friendGroups) {
            FriendGroup friendGroup = mongoFriendGroup.createFriendGroup();
            memberRelation.getGroups().add(friendGroup);
        }
        return memberRelation;
    }
}
