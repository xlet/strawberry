package cn.w.im.domains.relation;

import cn.w.im.domains.member.BasicMember;

import java.util.List;

/**
 * member recent contacts.
 */
public class RecentContacts {

    private BasicMember owner;
    private List<RecentContactItem> contacts;

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
     * @param owner
     */
    public void setOwner(BasicMember owner) {
        this.owner = owner;
    }

    /**
     * get contact collection.
     *
     * @return contact collection.
     */
    public List<RecentContactItem> getContacts() {
        return contacts;
    }

    /**
     * set contact collection.
     *
     * @param contacts contact collection.
     */
    public void setContacts(List<RecentContactItem> contacts) {
        this.contacts = contacts;
    }
}
