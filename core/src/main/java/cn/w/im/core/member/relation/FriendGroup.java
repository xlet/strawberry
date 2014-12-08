package cn.w.im.core.member.relation;

import cn.w.im.core.actionSupport.ActionType;
import cn.w.im.core.actionSupport.FriendGroupChangeLog;
import cn.w.im.core.actionSupport.FriendGroupContactChangeLog;
import cn.w.im.core.exception.ServerInnerException;
import cn.w.im.core.member.BasicMember;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

/**
 * friend group info.
 */
public class FriendGroup {

    private String id;
    private String name;
    @JsonIgnore
    private BasicMember owner;
    private Map<String, BasicMember> contacts;
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
     * @param name   namee.
     * @param owner  owner member.
     * @param system true:system group.
     */
    public FriendGroup(String id, String name, BasicMember owner, boolean system) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.system = system;
        this.contacts = new HashMap<String, BasicMember>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id").append(this.getId());
        sb.append("name").append(this.getName());
        sb.append("system").append(this.isSystem());
        return sb.toString();
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
     * get name.
     *
     * @return name.
     */
    public String getName() {
        return name;
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
     * get child contacts.
     *
     * @return child contacts.
     */
    public Collection<BasicMember> getContacts() {
        return contacts.values();
    }

    /**
     * initialize contacts not contact change log.
     *
     * @param contact contact.
     */
    public void initContact(BasicMember contact) {
        if (this.contacts.containsKey(contact.getId())) {
            this.contacts.put(contact.getId(), contact);
        }
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
     * add contact to friend group and record contact change log.
     *
     * @param contact contact.
     * @throws ServerInnerException server exception.
     */
    public void addContact(BasicMember contact) throws ServerInnerException {
        if (this.contacts.containsKey(contact.getId())) {
            this.contacts.put(contact.getId(), contact);

            FriendGroupContactChangeLog contactChangeLog = new FriendGroupContactChangeLog(contact, ActionType.Add);
            contactChangeLog.save();
        }

        throw new ContactExistsException(contact.getId());
    }

    /**
     * delete contact from friend group and record contact change log.
     *
     * @param contact contact.
     * @throws ServerInnerException server exception.
     */
    public void deleteContact(BasicMember contact) throws ServerInnerException {
        if (this.contacts.containsKey(contact.getId())) {
            this.contacts.remove(contact.getId());

            FriendGroupContactChangeLog contactChangeLog = new FriendGroupContactChangeLog(contact, ActionType.Delete);
            contactChangeLog.save();
        }

        throw new ContactNotExistedException(contact.getId());
    }

    /**
     * update friend group name.
     *
     * @param newName group new name.
     * @throws ServerInnerException
     */
    public void update(String newName) throws ServerInnerException {
        this.name = newName;
        FriendGroupChangeLog friendGroupChangeLog = new FriendGroupChangeLog(this, ActionType.Update);
        friendGroupChangeLog.save();
    }
}
