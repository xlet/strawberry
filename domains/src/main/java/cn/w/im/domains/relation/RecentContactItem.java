package cn.w.im.domains.relation;

import cn.w.im.domains.member.BasicMember;

/**
 * relation of members.
 */
public class RecentContactItem {

    private BasicMember owner;
    private BasicMember contact;
    private String lastMessage;
    private long lastLinkTime;

    /**
     * default constructor.
     */
    public RecentContactItem() {
    }

    /**
     * constructor.
     *
     * @param owner        owner.
     * @param contact      relation contact.
     * @param lastMessage  last message.
     * @param lastLinkTime last link time.
     */
    public RecentContactItem(BasicMember owner, BasicMember contact, String lastMessage, long lastLinkTime) {
        this.contact = contact;
        this.lastLinkTime = lastLinkTime;
        this.lastMessage = lastMessage;
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
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * set last message.
     *
     * @param lastMessage last message.
     */
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    /**
     * get last link time.
     *
     * @return last link time.
     */
    public long getLastLinkTime() {
        return lastLinkTime;
    }

    /**
     * set last link time.
     *
     * @param lastLinkTime last link time.
     */
    public void setLastLinkTime(long lastLinkTime) {
        this.lastLinkTime = lastLinkTime;
    }
}
