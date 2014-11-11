package cn.w.im.core.member;

import cn.w.im.core.Status;

/**
 * member status.
 */
public class MemberStatus {

    private BasicMember member;
    private Status status;

    /**
     * constructor.
     *
     * @param member member.
     * @param status status.
     */
    public MemberStatus(BasicMember member, Status status) {
        this.member = member;
        this.status = status;
    }

    public BasicMember getMember() {
        return member;
    }

    public void setMember(BasicMember member) {
        this.member = member;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
