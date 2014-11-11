package cn.w.im.core.member.relation;

import cn.w.im.core.member.BasicMember;

import java.util.Collection;

/**
 * member recent contactStatuses.
 */
public class RecentContactStatuses {

    private BasicMember owner;
    private Collection<RecentContactStatus> contactStatuses;

    public RecentContactStatuses() {
    }

    public RecentContactStatuses(BasicMember owner, Collection<RecentContactStatus> contactStatuses) {
        this.owner = owner;
        this.contactStatuses = contactStatuses;
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
    public Collection<RecentContactStatus> getContactStatuses() {
        return contactStatuses;
    }

    /**
     * set contact collection.
     *
     * @param contactStatuses contact collection.
     */
    public void setContactStatuses(Collection<RecentContactStatus> contactStatuses) {
        this.contactStatuses = contactStatuses;
    }
}
