package cn.w.im.core.message.client;

import cn.w.im.core.MessageType;
import cn.w.im.core.message.Message;
import cn.w.im.core.member.relation.RecentContactStatus;

import java.util.Collection;

/**
 * nearlyLinkman response message.
 * <p/>
 * when client connected message server,send this message to client.
 */
public class RecentContactsMessage extends Message implements ServerToClientMessage {

    private Collection<RecentContactStatus> recentContacts;

    public RecentContactsMessage() {
        super(MessageType.RecentContacts);
    }

    public RecentContactsMessage(Collection<RecentContactStatus> recentContacts) {
        this();
        this.recentContacts = recentContacts;
    }

    /**
     * get recent contacts.
     *
     * @return recent contacts.
     */
    public Collection<RecentContactStatus> getRecentContacts() {
        return recentContacts;
    }

    /**
     * set recent contacts.
     *
     * @param recentContacts recent contacts.
     */
    public void setRecentContacts(Collection<RecentContactStatus> recentContacts) {
        this.recentContacts = recentContacts;
    }
}
