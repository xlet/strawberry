package cn.w.im.domains.relation;

import cn.w.im.domains.member.BasicMember;
import cn.w.im.domains.member.OAMember;
import cn.w.im.domains.mongo.relation.MongoFriendGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * friend group info.
 */
public class FriendGroup {

    private String id;
    private String name;
    private BasicMember owner;
    private List<BasicMember> contacts;
    private boolean system;

    /**
     * default constructor.
     */
    public FriendGroup() {

    }

    /**
     * constructor.
     *
     * @param id     id.
     * @param name   name.
     * @param owner  owner member.
     * @param system true:system group.
     */
    public FriendGroup(String id, String name, BasicMember owner, boolean system) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.system = system;
    }

    /**
     * get id.
     *
     * @return id.
     */
    public String getId() {
        return id;
    }

    /**
     * set id.
     *
     * @param id id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * set name.
     *
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get owner member.
     *
     * @return owner member.
     */
    public BasicMember getOwner() {
        return owner;
    }

    /**
     * set owner member.
     *
     * @param owner owner member.
     */
    public void setOwner(BasicMember owner) {
        this.owner = owner;
    }

    /**
     * get child contacts.
     *
     * @return child contacts.
     */
    public List<BasicMember> getContacts() {
        return contacts;
    }

    /**
     * set child contacts.
     *
     * @param contacts child contacts.
     */
    public void setContacts(List<BasicMember> contacts) {
        this.contacts = contacts;
    }

    /**
     * get whether system group.
     *
     * @return true:system group.
     */
    public boolean isSystem() {
        return system;
    }

    /**
     * set whether system group.
     *
     * @param system true:system group.
     */
    public void setSystem(boolean system) {
        this.system = system;
    }


    public MongoFriendGroup createMongoFriendGroup() {
        MongoFriendGroup group = new MongoFriendGroup();
        group.setOwner(this.getOwner().getId());
        group.setId(this.getId());
        group.setName(this.getName());
        group.setSystem(this.isSystem());
        group.setContracts(new ArrayList<String>());
        for (BasicMember member : this.contacts) {
            group.getContracts().add(member.getId());
        }
        return group;
    }

    public void addContract(OAMember member) {
        if (this.contacts == null) {
            this.contacts = new ArrayList<BasicMember>();
        }
        this.contacts.add(member);
    }
}
