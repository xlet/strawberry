package cn.w.im.core.message.client;

import cn.w.im.core.status.recentContact.RecentContactStatus;

/**
 * {@link RecentContactStatus} summary.
 */
public class RecentContactStatusSummary {

    public String contactId;

    private long lastTime;

    private String lastContent;

    public RecentContactStatusSummary() {
    }

    public RecentContactStatusSummary(RecentContactStatus recentContactStatus) {
        this.contactId = recentContactStatus.getContact().getId();
        this.lastTime = recentContactStatus.getLastContactTime();
        this.lastContent = recentContactStatus.getLastMessageContent();
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastContent() {
        return lastContent;
    }

    public void setLastContent(String lastContent) {
        this.lastContent = lastContent;
    }
}
