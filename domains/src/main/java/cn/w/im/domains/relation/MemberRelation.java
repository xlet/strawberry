package cn.w.im.domains.relation;

import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.mongo.relation.MongoFriendGroup;
import cn.w.im.domains.mongo.relation.MongoMemberRelation;

import java.util.ArrayList;
import java.util.List;

/**
 * member relation.
 */
public class MemberRelation {

    private BasicMember owner;
    private List<FriendGroup> groups;
    private RecentContacts recentContacts;
    private long createTime;
    private long lastUpdateTime;
    private String version;

    public BasicMember getOwner() {
        return owner;
    }

    public void setOwner(BasicMember owner) {
        this.owner = owner;
    }

    public List<FriendGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<FriendGroup> groups) {
        this.groups = groups;
    }

    public RecentContacts getRecentContacts() {
        return recentContacts;
    }

    public void setRecentContacts(RecentContacts recentContacts) {
        this.recentContacts = recentContacts;
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

    public MongoMemberRelation createMongoMemberRelation() {
        MongoMemberRelation mongoMemberRelation = new MongoMemberRelation();
        mongoMemberRelation.setCreateTime(this.getCreateTime());
        mongoMemberRelation.setLastUpdateTime(this.getLastUpdateTime());
        mongoMemberRelation.setOwner(this.getOwner().getId());
        mongoMemberRelation.setVersion(this.getVersion());
        mongoMemberRelation.setFriendGroups(new ArrayList<MongoFriendGroup>());

        for (FriendGroup friendGroup : this.getGroups()) {
            MongoFriendGroup group = friendGroup.createMongoFriendGroup();
            mongoMemberRelation.getFriendGroups().add(group);
        }
        return mongoMemberRelation;
    }
}
