package cn.w.im.domains.messages.client;

import cn.w.im.domains.MessageType;
import cn.w.im.domains.messages.Message;
import cn.w.im.domains.relation.RecentContactItem;

import java.util.List;

/**
 * nearlyLinkman response message.
 * <p/>
 * when client connected message server,send this message to client.
 */
public class RecentContactsMessage extends Message implements ServerToClientMessage {

    private List<RecentContactItem> recentContacts;

    public RecentContactsMessage() {
        super(MessageType.RecentContacts);
    }

    public RecentContactsMessage(List<RecentContactItem> recentContacts) {
        this();
        this.recentContacts = recentContacts;
    }

    /**
     * get recent contacts.
     * @return recent contacts.
     */
    public List<RecentContactItem> getRecentContacts() {
        return recentContacts;
    }

    /**
     * set recent contacts.
     * @param recentContacts recent contacts.
     */
    public void setRecentContacts(List<RecentContactItem> recentContacts) {
        this.recentContacts = recentContacts;
    }
}
