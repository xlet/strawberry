package cn.w.im.domains.mongo.relation;

import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.member.NotInitMember;
import cn.w.im.domains.mongo.MongoDomain;
import cn.w.im.domains.relation.FriendGroup;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * friend group of mongo.
 */
@Entity("friendGroup")
public class MongoFriendGroup implements MongoDomain {

    @Id
    private ObjectId persistentId;
    private long persistentDate;

    private String id;
    private String name;
    private boolean system;
    private String owner;
    private List<String> contracts;


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getContracts() {
        return contracts;
    }

    public void setContracts(List<String> contracts) {
        this.contracts = contracts;
    }

    public FriendGroup createFriendGroup() {
        FriendGroup friendGroup = new FriendGroup();
        friendGroup.setId(this.getId());
        friendGroup.setName(this.getName());
        friendGroup.setOwner(new NotInitMember(this.getOwner()));
        friendGroup.setSystem(this.isSystem());
        friendGroup.setContacts(new ArrayList<BasicMember>());
        for (String contactId : this.getContracts()) {
            friendGroup.getContacts().add(new NotInitMember(contactId));
        }
        return friendGroup;
    }
}
