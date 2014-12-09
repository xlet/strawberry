package org.xlet.strawberry.core.message.client;

import org.xlet.strawberry.core.message.MessageType;
import org.xlet.strawberry.core.message.Message;
import org.xlet.strawberry.core.status.recentContact.RecentContactStatus;

import java.util.ArrayList;
import java.util.Collection;

/**
 * nearlyLinkman response message.
 * <p/>
 * when client connected message server,send this message to client.
 */
public class RecentContactsMessage extends Message implements ServerToClientMessage {

    private Collection<RecentContactStatusSummary> recentContacts;

    public RecentContactsMessage() {
        super(MessageType.RecentContacts);
        this.recentContacts = new ArrayList<RecentContactStatusSummary>();
    }

    public RecentContactsMessage(Collection<RecentContactStatus> recentContacts) {
        this();
        for (RecentContactStatus recentContactStatus : recentContacts) {
            RecentContactStatusSummary summary = new RecentContactStatusSummary(recentContactStatus);
            this.recentContacts.add(summary);
        }
    }

    /**
     * get recent contacts.
     *
     * @return recent contacts.
     */
    public Collection<RecentContactStatusSummary> getRecentContacts() {
        return recentContacts;
    }

    /**
     * set recent contacts.
     *
     * @param recentContacts recent contacts.
     */
    public void setRecentContacts(Collection<RecentContactStatusSummary> recentContacts) {
        this.recentContacts = recentContacts;
    }
}
