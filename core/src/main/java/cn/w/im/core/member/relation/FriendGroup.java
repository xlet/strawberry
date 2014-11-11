package cn.w.im.core.member.relation;

import cn.w.im.core.member.BasicMember;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * friend group info.
 */
public class FriendGroup {

    private String id;
    private String name;
    @JsonIgnore
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

    public void addContract(BasicMember member) {
        if (this.contacts == null) {
            this.contacts = new ArrayList<BasicMember>();
        }
        this.contacts.add(member);
    }
}
