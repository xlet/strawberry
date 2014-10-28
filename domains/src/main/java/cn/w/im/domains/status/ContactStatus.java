package cn.w.im.domains.status;

import cn.w.im.domains.member.BasicMember;

/**
 * contact status.
 */
public class ContactStatus {

    private BasicMember member1;
    private BasicMember member2;
    private long lastContactTime;
    private String lastMessage;

    public BasicMember getMember1() {
        return member1;
    }

    public void setMember1(BasicMember member1) {
        this.member1 = member1;
    }

    public BasicMember getMember2() {
        return member2;
    }

    public void setMember2(BasicMember member2) {
        this.member2 = member2;
    }

    public long getLastContactTime() {
        return lastContactTime;
    }

    public void setLastContactTime(long lastContactTime) {
        this.lastContactTime = lastContactTime;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
