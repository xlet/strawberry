package cn.w.im.core.providers.status;

import cn.w.im.core.member.BasicMember;

/**
 * cached recent contact status.
 */
public class CacheRecentContactStatus {

    private BasicMember memberA;

    private BasicMember memberB;

    private long lastContactTime;

    private String lastMessageContent;

    public CacheRecentContactStatus(BasicMember memberA, BasicMember memberB, long lastContactTime, String lastMessageContent) {
        this.memberA = memberA;
        this.memberB = memberB;
        this.lastContactTime = lastContactTime;
        this.lastMessageContent = lastMessageContent;
    }

    public BasicMember getMemberA() {
        return memberA;
    }

    public BasicMember getMemberB() {
        return memberB;
    }

    public long getLastContactTime() {
        return lastContactTime;
    }

    public void setLastContactTime(long lastContactTime) {
        this.lastContactTime = lastContactTime;
    }

    public String getLastMessageContent() {
        return lastMessageContent;
    }

    public void setLastMessageContent(String lastMessageContent) {
        this.lastMessageContent = lastMessageContent;
    }

    public BasicMember getContact(BasicMember owner) {
        if (this.memberA.getId().equals(owner.getId())) {
            return this.memberB;
        }
        return memberA;
    }
}
