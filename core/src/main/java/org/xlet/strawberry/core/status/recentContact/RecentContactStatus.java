package org.xlet.strawberry.core.status.recentContact;

import org.xlet.strawberry.core.member.BasicMember;

/**
 * member's recent contact status.
 */
public class RecentContactStatus {

    private BasicMember owner;
    private BasicMember contact;
    private String lastMessageContent;
    private long lastContactTime;

    /**
     * default constructor.
     */
    public RecentContactStatus() {
    }

    /**
     * constructor.
     *
     * @param owner              owner.
     * @param contact            relation contact.
     * @param lastMessageContent last message.
     * @param lastContactTime    last link time.
     */
    public RecentContactStatus(BasicMember owner, BasicMember contact, String lastMessageContent, long lastContactTime) {
        this.contact = contact;
        this.lastContactTime = lastContactTime;
        this.lastMessageContent = lastMessageContent;
        this.owner = owner;
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
     * get relation contact.
     *
     * @return relation contact.
     */
    public BasicMember getContact() {
        return contact;
    }

    /**
     * set relation contact.
     *
     * @param contact relation contact.
     */
    public void setContact(BasicMember contact) {
        this.contact = contact;
    }

    /**
     * get last message.
     *
     * @return last message.
     */
    public String getLastMessageContent() {
        return lastMessageContent;
    }

    /**
     * set last message.
     *
     * @param lastMessageContent last message.
     */
    public void setLastMessageContent(String lastMessageContent) {
        this.lastMessageContent = lastMessageContent;
    }

    /**
     * get last link time.
     *
     * @return last link time.
     */
    public long getLastContactTime() {
        return lastContactTime;
    }

    /**
     * set last link time.
     *
     * @param lastContactTime last link time.
     */
    public void setLastContactTime(long lastContactTime) {
        this.lastContactTime = lastContactTime;
    }
}
